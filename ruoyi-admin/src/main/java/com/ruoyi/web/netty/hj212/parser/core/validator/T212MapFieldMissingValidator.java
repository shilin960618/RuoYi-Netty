package com.ruoyi.web.netty.hj212.parser.core.validator;



import com.ruoyi.web.netty.hj212.parser.model.DataFlag;
import com.ruoyi.web.netty.hj212.parser.model.verify.DataElement;
import com.ruoyi.web.netty.hj212.parser.model.verify.T212Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created on 2018/1/10.
 */
public class T212MapFieldMissingValidator
        implements ConstraintValidator<FieldMissing, T212Map> {

    private FieldMissing fieldMissing;


    @Override
    public void initialize(FieldMissing fieldMissing) {
        this.fieldMissing = fieldMissing;
    }

    @Override
    public boolean isValid(T212Map value, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, String> result = value;

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
//            T212FormatException.field_is_missing(PacketElement.DATA,missing.get().name());
            return false;
        }
        return true;
    }

}
