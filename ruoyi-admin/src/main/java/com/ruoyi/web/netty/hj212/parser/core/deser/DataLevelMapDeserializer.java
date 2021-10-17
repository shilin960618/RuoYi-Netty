package com.ruoyi.web.netty.hj212.parser.core.deser;



import com.ruoyi.web.netty.hj212.parser.core.T212Parser;
import com.ruoyi.web.netty.hj212.parser.core.VerifyUtil;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.core.validator.clazz.FieldValidator;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.DataFlag;
import com.ruoyi.web.netty.hj212.parser.model.verify.DataElement;
import com.ruoyi.web.netty.hj212.parser.model.verify.PacketElement;
import com.ruoyi.web.netty.hj212.parser.model.verify.T212Map;
import com.ruoyi.web.netty.hj212.parser.model.verify.T212MapEntry;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.ModeGroup;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.T212MapLevelGroup;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.VersionGroup;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configurator;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configured;
import com.ruoyi.web.netty.hj212.segmentParser.core.SegmentParser;
import com.ruoyi.web.netty.hj212.segmentParser.core.deser.SegmentDeserializer;
import com.ruoyi.web.netty.hj212.segmentParser.exception.SegmentFormatException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ruoyi.web.netty.hj212.parser.core.T212Parser.crc16Checkout;


/**
 * 数据段 级别 反序列化器
 * Created on 2017/12/15.
 */
public class DataLevelMapDeserializer
        implements T212Deserializer<Map<String,String>>, Configured<DataLevelMapDeserializer> {

    private int verifyFeature;
    private Configurator<SegmentParser> segmentParserConfigurator;
    private SegmentDeserializer<Map<String,String>> segmentDeserializer;
    private Validator validator;

//    private int version;


    @Override
    public void configured(Configurator<DataLevelMapDeserializer> configurator){
        configurator.config(this);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Map<String, String> deserialize(T212Parser parser) throws IOException, T212FormatException {
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

    public Map<String, String> deserialize(char[] data) throws IOException, T212FormatException {
        PushbackReader reader = new PushbackReader(new CharArrayReader(data));
        SegmentParser parser = new SegmentParser(reader);
        parser.configured(segmentParserConfigurator);

        Map<String,String> result = null;
        try {
            result = segmentDeserializer.deserialize(parser);
        } catch (SegmentFormatException e) {
            T212FormatException.segment_exception(e);
        }

        if(VerifyFeature.USE_VERIFICATION.enabledIn(verifyFeature)){
            verifyByType(result);
        }
        return result;
    }

    private void verifyByType(Map<String, String> result) throws T212FormatException {
        T212Map t212Map = T212Map.createDataLevel(result);

        List<Class> groups = new ArrayList<>();
        groups.add(Default.class);
        int flag = 0;
        if(result.containsKey(DataElement.Flag.name())){
            String f = result.get(DataElement.Flag.name());
            flag = Integer.valueOf(f);
        }
        if(DataFlag.V0.isMarked(flag)){
            groups.add(VersionGroup.V2017.class);
        }else{
            groups.add(VersionGroup.V2005.class);
        }
        if(DataFlag.D.isMarked(flag)){
            groups.add(ModeGroup.UseSubPacket.class);
        }

        Set<ConstraintViolation<T212Map>> constraintViolationSet =
                validator.validate(t212Map,groups.toArray(new Class[]{}));
        if(!constraintViolationSet.isEmpty()) {
            if(VerifyFeature.THROW_ERROR_VERIFICATION_FAILED.enabledIn(verifyFeature)){
                FieldValidator.create_format_exception(constraintViolationSet,result);
            }else{
                //TODO set context
            }
        }
    }

    @Deprecated
    private void verifyByVersion(Map<String, String> result) throws T212FormatException {
        List<Class> groups = new ArrayList<>();
        groups.add(Default.class);
        groups.add(T212MapLevelGroup.DataLevel.class);

        int flag = 0;
        T212Map t212Map;
        if(result.containsKey(DataElement.Flag.name())){
            String f = result.get(DataElement.Flag.name());
            flag = Integer.valueOf(f);
        }
        if(DataFlag.V0.isMarked(flag)){
            t212Map = T212Map.create2017(result);
        }else{
            t212Map = T212Map.create2005(result);
        }
        if(DataFlag.D.isMarked(flag)){
            groups.add(ModeGroup.UseSubPacket.class);
        }

        Set<ConstraintViolation<T212Map>> constraintViolationSet = validator.validate(t212Map,groups.toArray(new Class[]{}));
        if(!constraintViolationSet.isEmpty()) {
            FieldValidator.create_format_exception(constraintViolationSet,result);
        }
    }

    @Deprecated
    private void verify(Map<String, String> result) throws T212FormatException {
        if(!VerifyFeature.ALLOW_MISSING_FIELD.enabledIn(verifyFeature)){
            Stream<DataElement> stream = Stream.of(DataElement.values())
                    .filter(DataElement::isRequired);
            if(result.containsKey(DataElement.Flag.name())){
                String f = result.get(DataElement.Flag.name());
                int flag = Integer.valueOf(f);
                if(DataFlag.D.isMarked(flag)){
                    stream = Stream.concat(stream,Stream.of(DataElement.PNO, DataElement.PNUM));
                }
            }

            Optional<DataElement> missing = stream
                    .filter(e -> !result.containsKey(e.name()))
                    .findFirst();
            if(missing.isPresent()){
                T212FormatException.field_is_missing(PacketElement.DATA,missing.get().name());
            }
        }
//
//        if(!ALLOW_VALUE_NOT_IN_RANGE.enabledIn(verifyFeature)){
//            Optional<DataElement> notInRange = result.entrySet()
//                    .stream()
//                    .map(kv -> new AbstractMap.SimpleEntry<>(
//                            DataElement.valueOf(kv.getKey()),
//                            kv.getValue()
//                    ))
//                    .filter(kv -> kv.getKey() != null)
//                    .filter(kv -> !kv.getKey().isVerify(kv.getValue()))
//                    .map(AbstractMap.SimpleEntry::getKey)
//                    .findFirst();
//            if(notInRange.isPresent()){
//                DataElement dataSchema = notInRange.get();
//                String value = result.get(dataSchema.name());
//                int len = value == null ? 0 : value.length();
//                T212FormatException.length_not_range(PacketElement.DATA,len,dataSchema.getMin(),dataSchema.getMax());
//            }
//        }

        if(!VerifyFeature.ALLOW_VALUE_NOT_IN_RANGE.enabledIn(verifyFeature)){
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Stream<Set<ConstraintViolation<T212MapEntry>>> v = result.entrySet()
                    .stream()
                    .map(kv -> T212MapEntry.of(kv.getKey(),kv.getValue()))
                    .map(e -> {
                        return validator.validate(e, DataElement.valueOf(e.getKey()).group());
                    });
            Set<ConstraintViolation<T212MapEntry>> constraintViolationSet;
            if(VerifyFeature.STRICT.enabledIn(verifyFeature)){
                constraintViolationSet = v
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
            }else{
                constraintViolationSet = v
                        .findAny()
                        .orElse(new HashSet<>());
            }

            if(!constraintViolationSet.isEmpty()){
                String msg = constraintViolationSet.stream()
                        .map(cv -> {
                            return cv.getRootBean().getKey()
                                    + ":"
                                    + cv.getInvalidValue().toString()
                                    + "_"
                                    + cv.getMessage();
                        })
                        .collect(Collectors.joining("\n"));
                throw new T212FormatException("Validate error\n\n" + msg);
            }
        }
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setSegmentParserConfigurator(Configurator<SegmentParser> segmentParserConfigurator) {
        this.segmentParserConfigurator = segmentParserConfigurator;
    }

    public void setSegmentDeserializer(SegmentDeserializer<Map<String, String>> segmentDeserializer) {
        this.segmentDeserializer = segmentDeserializer;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

}
