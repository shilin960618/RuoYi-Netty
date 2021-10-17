package com.ruoyi.web.netty.hj212.parser.core.validator.field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created on 2018/1/10.
 */
public class CValidator implements ConstraintValidator<C, String> {

    private int max;

    @Override
    public void initialize(C validDate) {
        this.max = validDate.len();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        int len;
        if(value == null){
            len = 0;
        }else{
            len = value.length();
        }
        return len <= max;
    }

}
