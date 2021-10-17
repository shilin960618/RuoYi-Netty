package com.ruoyi.web.netty.Test.parser.core.deser;



import com.ruoyi.web.netty.hj212.parser.core.T212Parser;
import com.ruoyi.web.netty.hj212.parser.core.cfger.T212Configurator;
import com.ruoyi.web.netty.hj212.parser.core.deser.DataLevelMapDeserializer;
import com.ruoyi.web.netty.hj212.parser.core.feature.ParserFeature;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Feature;


import javax.validation.Validation;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created on 2018/1/11.
 */
public class DataLevelMapDeserializerTest {

    @Test
    public void testDeserialize(){
        String data = "##0139ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n";
        StringReader reader = new StringReader(data);
        T212Parser t212Parser = new T212Parser(reader);
        T212Configurator configurator = new T212Configurator();
        int vFeature = Feature.collectFeatureDefaults(VerifyFeature.class);
        vFeature = vFeature | VerifyFeature.ALLOW_MISSING_FIELD.getMask();
        vFeature = vFeature | VerifyFeature.ALLOW_VALUE_NOT_IN_RANGE.getMask();
        configurator.setVerifyFeature(vFeature);
        configurator.setValidator(Validation.buildDefaultValidatorFactory().getValidator());
        configurator.setParserFeature(Feature.collectFeatureDefaults(ParserFeature.class));
        DataLevelMapDeserializer deserializer = new DataLevelMapDeserializer();
        deserializer.configured(configurator);

        try (T212Parser parser = t212Parser) {
            Map<String, String> map = deserializer.deserialize(parser);

            assertNotNull(map);
            assertEquals(map.get("ST"),"32");
            assertEquals(map.get("CN"),"2011");
            assertEquals(map.get("PW"),"123456");
            assertEquals(map.get("MN"),"LD130133000015");
            assertEquals(map.get("CP"),"DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N");
        } catch (T212FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFeature_USE_VERIFICATION(){
        String data = "##0139ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n";

        //CP length cant bigger than 960 in version 2005
        int l = 1024 - 139;
        String a = String.join("", Collections.nCopies(l,"#"));
        data = data.replace("060-Flag=N&&4980","060-Flag=N" + a + "&&4980");
        data = data.replace("0139ST=32;","1024ST=32;");

        T212Configurator configurator = new T212Configurator();
        configurator.setValidator(Validation.buildDefaultValidatorFactory().getValidator());
        int vFeature = VerifyFeature.THROW_ERROR_VERIFICATION_FAILED.getMask();
        configurator.setVerifyFeature(vFeature);

        StringReader reader = new StringReader(data);
        T212Parser parser = new T212Parser(reader);
        DataLevelMapDeserializer deserializer = new DataLevelMapDeserializer();
        deserializer.configured(configurator);
        try (T212Parser p = parser) {
            String cp = deserializer.deserialize(p).get("CP");
            assertTrue(cp.length() > 960);
        } catch (T212FormatException | IOException e) {
            assert false;
        }

        vFeature = vFeature | VerifyFeature.USE_VERIFICATION.getMask();
        configurator.setVerifyFeature(vFeature);

        reader = new StringReader(data);
        parser = new T212Parser(reader);
        deserializer = new DataLevelMapDeserializer();
        deserializer.configured(configurator);
        try (T212Parser p = parser) {
            deserializer.deserialize(p);
            assert false;
        } catch (T212FormatException | IOException e) {
            assertTrue(e.getMessage().contains("Validate"));
        }
    }

}