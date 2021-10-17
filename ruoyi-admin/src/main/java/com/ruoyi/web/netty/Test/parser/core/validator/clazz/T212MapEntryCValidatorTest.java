package com.ruoyi.web.netty.Test.parser.core.validator.clazz;

import com.ruoyi.web.netty.hj212.parser.model.verify.T212MapEntry;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.TypeGroup;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;



/**
 * Created on 2018/1/11.
 */
public class T212MapEntryCValidatorTest {

    @Test
    public void test(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T212MapEntry>> e1 = validator.validate(T212MapEntry.of("C1","U"), TypeGroup.C1.class);
        assertTrue(e1.isEmpty());
        Set<ConstraintViolation<T212MapEntry>> e2 = validator.validate(T212MapEntry.of("C6","QWERTY"),TypeGroup.C6.class);
        assertTrue(e2.isEmpty());
    }

}