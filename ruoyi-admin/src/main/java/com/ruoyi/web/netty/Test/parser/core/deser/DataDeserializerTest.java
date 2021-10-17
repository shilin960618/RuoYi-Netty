package com.ruoyi.web.netty.Test.parser.core.deser;



import com.ruoyi.web.netty.hj212.parser.core.T212Parser;
import com.ruoyi.web.netty.hj212.parser.core.cfger.T212Configurator;
import com.ruoyi.web.netty.hj212.parser.core.deser.DataDeserializer;
import com.ruoyi.web.netty.hj212.parser.core.feature.ParserFeature;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.CpData;
import com.ruoyi.web.netty.hj212.parser.model.Data;
import com.ruoyi.web.netty.hj212.parser.model.Pollution;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Feature;
import com.ruoyi.web.netty.hj212.segmentParser.core.feature.SegmentParserFeature;


import javax.validation.Validation;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * Created on 2018/1/11.
 */
public class DataDeserializerTest {

    @Test
    public void test(){
        String t212 = "##0139ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n";
        StringReader reader = new StringReader(t212);
        T212Parser parser = new T212Parser(reader);
        DataDeserializer deserializer = new DataDeserializer();

        T212Configurator configurator = new T212Configurator();
        configurator.setVerifyFeature(Feature.collectFeatureDefaults(VerifyFeature.class));
        configurator.setParserFeature(Feature.collectFeatureDefaults(ParserFeature.class));
        configurator.setSegmentParserFeature(Feature.collectFeatureDefaults(SegmentParserFeature.class));
        configurator.setValidator(Validation.buildDefaultValidatorFactory().getValidator());

        parser.configured(configurator);
        deserializer.configured(configurator);

        try (T212Parser p = parser) {
            Data data = deserializer.deserialize(p);

            assertNotNull(data);
            assertEquals(data.getSt(),"32");
            assertEquals(data.getCn(),"2011");
            assertEquals(data.getPw(),"123456");
            assertEquals(data.getMn(),"LD130133000015");

            CpData cp = data.getCp();
            assertEquals(cp.getDataTime(),"20160824003817000");
            Map<String, Pollution> pollutionMap = cp.getPollution();

            Pollution pB01 = pollutionMap.get("B01");
            assertEquals(pB01.getRtd(),new BigDecimal("36.91"));

            Pollution p011 = pollutionMap.get("011");
            assertEquals(p011.getRtd(),new BigDecimal("231.0"));
            assertEquals(p011.getFlag(),"N");

            Pollution p060 = pollutionMap.get("060");
            assertEquals(p060.getRtd(),new BigDecimal("1.803"));
            assertEquals(p060.getFlag(),"N");
        } catch (T212FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}