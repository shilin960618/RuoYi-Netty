package com.ruoyi.web.netty.hj212.parser.core.deser;



import com.ruoyi.web.netty.hj212.parser.core.T212Parser;
import com.ruoyi.web.netty.hj212.parser.core.VerifyUtil;
import com.ruoyi.web.netty.hj212.parser.core.converter.DataConverter;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.core.validator.clazz.FieldValidator;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.Data;
import com.ruoyi.web.netty.hj212.parser.model.DataFlag;
import com.ruoyi.web.netty.hj212.parser.model.verify.PacketElement;
import com.ruoyi.web.netty.hj212.parser.model.verify.T212Map;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.ModeGroup;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.VersionGroup;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configurator;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configured;
import com.ruoyi.web.netty.hj212.segmentParser.core.SegmentParser;
import com.ruoyi.web.netty.hj212.segmentParser.core.deser.SegmentDeserializer;
import com.ruoyi.web.netty.hj212.segmentParser.exception.SegmentFormatException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ruoyi.web.netty.hj212.parser.core.T212Parser.crc16Checkout;


/**
 * 对象 级别 反序列化器
 * Created on 2017/12/15.
 */
public class DataDeserializer
        implements T212Deserializer<Data>, Configured<DataDeserializer> {

    private int verifyFeature;
    private Configurator<SegmentParser> segmentParserConfigurator;
    private SegmentDeserializer<Map<String,Object>> segmentDeserializer;
    private Configurator<DataConverter> dataConverterConfigurator;
    private Validator validator;

//    private int version;


    @Override
    public void configured(Configurator<DataDeserializer> configurator){
        configurator.config(this);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Data deserialize(T212Parser parser) throws IOException, T212FormatException {
        parser.readHeader();
        int len = parser.readInt32(10);
        if(len == -1){
            T212FormatException.length_not_range(PacketElement.DATA_LEN,len,4,4);
        }
        if(VerifyFeature.DATA_LEN_RANGE.enabledIn(verifyFeature)){
            VerifyUtil.verifyRange(len,0,1024, PacketElement.DATA_LEN);
        }
        char[] data = parser.readData(len);
        int crc = parser.readInt32(16);

        if(VerifyFeature.DATA_CRC.enabledIn(verifyFeature)){
            if(crc == -1 ||
                    crc16Checkout(data,len) != crc){
                T212FormatException.crc_verification_failed(PacketElement.DATA,data,crc);
            }
        }
        parser.readFooter();
        return deserialize(data);
    }

    public Data deserialize(char[] data) throws IOException, T212FormatException {
        PushbackReader reader = new PushbackReader(new CharArrayReader(data));
        SegmentParser parser = new SegmentParser(reader);
        parser.configured(segmentParserConfigurator);

        Map<String,Object> result = null;
        try {
            result = segmentDeserializer.deserialize(parser);
        } catch (SegmentFormatException e) {
            T212FormatException.segment_exception(e);
        }
        return deserialize(result);
    }

    public Data deserialize(Map<String,Object> map) throws T212FormatException {
        DataConverter dataConverter = new DataConverter();
        dataConverter.configured(dataConverterConfigurator);
        Data result = dataConverter.convert(T212Map.createCpDataLevel(map));

        if(VerifyFeature.USE_VERIFICATION.enabledIn(verifyFeature)){
            verify(result);
        }
        return result;
    }

    private void verify(Data result) throws T212FormatException {
        List<Class> groups = new ArrayList<>();
        groups.add(Default.class);
        if(DataFlag.V0.isMarked(result.getDataFlag())){
            groups.add(VersionGroup.V2017.class);
        }else{
            groups.add(VersionGroup.V2005.class);
        }
        if(DataFlag.D.isMarked(result.getDataFlag())){
            groups.add(ModeGroup.UseSubPacket.class);
        }

        Set<ConstraintViolation<Data>> constraintViolationSet =
                validator.validate(result,groups.toArray(new Class[]{}));
        if(!constraintViolationSet.isEmpty()) {
            if(VerifyFeature.THROW_ERROR_VERIFICATION_FAILED.enabledIn(verifyFeature)){
                FieldValidator.create_format_exception(constraintViolationSet,result);
            }else{
                //TODO set context
            }
        }
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setSegmentParserConfigurator(Configurator<SegmentParser> segmentParserConfigurator) {
        this.segmentParserConfigurator = segmentParserConfigurator;
    }

    public void setSegmentDeserializer(SegmentDeserializer<Map<String, Object>> segmentDeserializer) {
        this.segmentDeserializer = segmentDeserializer;
    }

    public void setDataConverterConfigurator(Configurator<DataConverter> mapperConfigurator) {
        this.dataConverterConfigurator = mapperConfigurator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

}
