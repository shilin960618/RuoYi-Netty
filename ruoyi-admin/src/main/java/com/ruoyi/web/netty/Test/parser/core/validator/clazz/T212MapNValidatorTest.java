package com.ruoyi.web.netty.Test.parser.core.validator.clazz;





import com.ruoyi.web.netty.hj212.parser.model.verify.T212Map;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.ModeGroup;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;




/**
 * Created on 2018/1/12.
 */
public class T212MapNValidatorTest {

    public Map<String,String> map;

    @Before
    public void init(){
        map = new HashMap<>();
        map.put("PNO","1234");
        map.put("PNUM","12340");
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void test(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        T212Map t212Map = T212Map.createDataLevel(map);

        Set<ConstraintViolation<T212Map>> e1 = validator.validate(t212Map,
                Default.class, ModeGroup.UseSubPacket.class);
        assertEquals(e1.size(),1);
    }
}