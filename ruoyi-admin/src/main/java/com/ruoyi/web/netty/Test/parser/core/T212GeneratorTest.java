package com.ruoyi.web.netty.Test.parser.core;


import com.ruoyi.web.netty.hj212.parser.core.T212Generator;
import com.ruoyi.web.netty.hj212.parser.core.T212Mapper;
import com.ruoyi.web.netty.hj212.parser.core.feature.GeneratorFeature;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.CpData;
import com.ruoyi.web.netty.hj212.parser.model.Data;
import com.ruoyi.web.netty.hj212.parser.model.DataFlag;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Feature;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created on 2018/2/24.
 */
public class T212GeneratorTest {

    @Test
    public void generate() {
        String data = "ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&";
        StringWriter writer = new StringWriter();
        T212Generator generator = new T212Generator(writer);
        generator.setGeneratorFeature(Feature.collectFeatureDefaults(GeneratorFeature.class));
        try {
            assertEquals(generator.writeHeader(),2);
            assertEquals(generator.writeDataLen(new char[]{'0','1','3','9'}),4);
            assertEquals(generator.writeData(data.toCharArray()),139);
            assertEquals(generator.writeCrc(new char[]{'4','9','8','0'}),4);
            assertEquals(generator.writeFooter(),2);

            assertEquals(writer.toString(),"##0139" + data + "4980\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            generator.close();
        }
    }

    @Test
    public void crc() {
        String data = "ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&";
        StringWriter writer = new StringWriter();
        T212Generator generator = new T212Generator(writer);
        generator.setGeneratorFeature(Feature.collectFeatureDefaults(GeneratorFeature.class));
        try {
            assertEquals(generator.writeHeader(),2);
            assertEquals(generator.writeDataAndLenAndCrc(data.toCharArray()),139 + 4+ 4);
            assertEquals(generator.writeFooter(),2);

            assertEquals(writer.toString(),"##0139" + data + "4980\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            generator.close();
        }
    }


    // 生产回传对象
    @Test
    public void testFeature_ALLOW_KEY_NOT_CLOSED1234() throws IOException, T212FormatException {

        List<DataFlag> dataFlagList = new ArrayList<>();
        dataFlagList.add(DataFlag.V0);
        Data dataResult = new Data();
        dataResult.setQn("11111111");
        dataResult.setSt("32");
        dataResult.setCn("9014");
        dataResult.setPw("123456");
        dataResult.setMn("LD130133000015");
        dataResult.setDataFlag(dataFlagList);
        CpData cp = new CpData();
        dataResult.setCp(cp);


        T212Mapper mapper = new T212Mapper()
                .enableDefaultParserFeatures()
                .enableDefaultVerifyFeatures();

        String result = mapper.writeDataAsString(dataResult);
        System.out.println(result + "111111");
    }

}