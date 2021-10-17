package com.ruoyi.web.netty.Test.parser.core.ser;



import com.ruoyi.web.netty.hj212.parser.core.T212Generator;
import com.ruoyi.web.netty.hj212.parser.core.cfger.T212Configurator;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.core.ser.PackLevelSerializer;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.Pack;
import org.junit.Test;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created on 2018/2/24.
 */
public class PackLevelSerializerTest {

    @Test
    public void test(){
        String data = "ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&";
        StringWriter writer = new StringWriter();
        T212Generator t212Generator = new T212Generator(writer);
        T212Configurator configurator = new T212Configurator();
        PackLevelSerializer serializer = new PackLevelSerializer();
        serializer.configured(configurator);

        try (T212Generator generator = t212Generator) {
            Pack pack = new Pack();
            pack.setData(data.toCharArray());
            serializer.serialize(generator,pack);

            assertEquals(writer.toString(),"##0139" + data + "4980\r\n");
        } catch (T212FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFeature_DATA_LEN_RANGE() {
        String data = "ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&";
        String a = String.join("", Collections.nCopies(1024 - 139 + 1,"#"));
        data = data.replace("060-Flag=N&&","060-Flag=N" + a + "&&");

        StringWriter writer = new StringWriter();
        T212Generator generator = new T212Generator(writer);
        T212Configurator configurator = new T212Configurator();
        PackLevelSerializer serializer = new PackLevelSerializer();
        serializer.configured(configurator);
        serializer.setVerifyFeature(VerifyFeature.DATA_LEN_RANGE.getMask());
        try (T212Generator g = generator) {
            Pack pack = new Pack();
            pack.setData(data.toCharArray());
            serializer.serialize(g,pack);
        } catch (T212FormatException | IOException e) {
            assertTrue(e.getMessage().contains("Length"));
        }
    }

    @Test
    public void testFeature_DATA_CRC() {
        String data = "ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&";

        StringWriter writer = new StringWriter();
        T212Generator generator = new T212Generator(writer);
        T212Configurator configurator = new T212Configurator();
        PackLevelSerializer serializer = new PackLevelSerializer();
        serializer.configured(configurator);
        serializer.setVerifyFeature(VerifyFeature.DATA_CRC.getMask());
        try (T212Generator g = generator) {
            Pack pack = new Pack();
            pack.setData(data.toCharArray());
            pack.setCrc("1111".toCharArray());
            serializer.serialize(g,pack);
        } catch (T212FormatException | IOException e) {
            assertTrue(e.getMessage().contains("Crc"));
        }
    }
}