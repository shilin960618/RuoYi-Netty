package com.ruoyi.web.netty.hj212.parser.core.ser;




import com.ruoyi.web.netty.hj212.parser.core.T212Generator;
import com.ruoyi.web.netty.hj212.parser.core.VerifyUtil;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.verify.PacketElement;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configurator;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configured;
import com.ruoyi.web.netty.hj212.segmentParser.core.SegmentGenerator;
import com.ruoyi.web.netty.hj212.segmentParser.core.ser.SegmentSerializer;
import com.ruoyi.web.netty.hj212.segmentParser.exception.SegmentFormatException;

import javax.validation.Validator;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * 数据区 级别 序列化器
 * Created on 2017/12/15.
 */
public class CpDataLevelMapDataSerializer
        implements T212Serializer<Map<String,Object>>, Configured<CpDataLevelMapDataSerializer> {

    private int verifyFeature;
    private Configurator<SegmentGenerator> segmentGeneratorConfigurator;
    private SegmentSerializer<Map<String,Object>> segmentSerializer;
    private Validator validator;

    @Override
    public void configured(Configurator<CpDataLevelMapDataSerializer> configurator){
        configurator.config(this);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void serialize(T212Generator generator, Map<String,Object> data) throws IOException, T212FormatException {
        generator.writeHeader();

        char[] segment = serialize(data);

        if(VerifyFeature.DATA_LEN_RANGE.enabledIn(verifyFeature)){
            int segmentLen = segment.length;
            VerifyUtil.verifyRange(segmentLen,0,1024, PacketElement.DATA_LEN);
        }
        generator.writeDataAndLenAndCrc(segment);
        generator.writeFooter();
    }

    public char[] serialize(Map<String, Object> data) throws IOException, T212FormatException {
        StringWriter writer = new StringWriter();
        SegmentGenerator generator = new SegmentGenerator(writer);
        generator.configured(segmentGeneratorConfigurator);

        try {
            segmentSerializer.serialize(generator,data);
        } catch (SegmentFormatException e) {
            T212FormatException.segment_exception(e);
        }
        return writer.toString().toCharArray();
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setSegmentGeneratorConfigurator(Configurator<SegmentGenerator> segmentGeneratorConfigurator) {
        this.segmentGeneratorConfigurator = segmentGeneratorConfigurator;
    }

    public void setSegmentSerializer(SegmentSerializer<Map<String, Object>> segmentSerializer) {
        this.segmentSerializer = segmentSerializer;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

}
