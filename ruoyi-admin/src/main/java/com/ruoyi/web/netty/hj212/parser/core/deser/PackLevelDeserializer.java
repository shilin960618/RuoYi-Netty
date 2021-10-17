package com.ruoyi.web.netty.hj212.parser.core.deser;



import com.ruoyi.web.netty.hj212.parser.core.T212Parser;
import com.ruoyi.web.netty.hj212.parser.core.VerifyUtil;
import com.ruoyi.web.netty.hj212.parser.core.feature.VerifyFeature;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.Pack;
import com.ruoyi.web.netty.hj212.parser.model.verify.PacketElement;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configurator;
import com.ruoyi.web.netty.hj212.segmentParser.cfger.Configured;

import java.io.IOException;

/**
 * 通信包 级别 反序列化器
 * Created on 2017/12/15.
 */
public class PackLevelDeserializer
        implements T212Deserializer<Pack>, Configured<PackLevelDeserializer> {

    private Configurator<T212Parser> parserConfigurator;
    private int verifyFeature;

    @Override
    public void configured(Configurator<PackLevelDeserializer> configurator){
        configurator.config(this);
    }

    @Override
    public Pack deserialize(T212Parser parser) throws IOException, T212FormatException {
        parser.configured(parserConfigurator);

        Pack pack = new Pack();
        pack.setHeader(parser.readHeader());
        pack.setLength(parser.readDataLen());

        int segmentLen = Integer.parseInt(new String(pack.getLength()));
        if(VerifyFeature.DATA_LEN_RANGE.enabledIn(verifyFeature)){
            VerifyUtil.verifyRange(segmentLen,0,1024, PacketElement.DATA_LEN);
        }
        pack.setData(parser.readData(segmentLen));
        pack.setCrc(parser.readCrc());

        if(VerifyFeature.DATA_CRC.enabledIn(verifyFeature)){
            VerifyUtil.verifyCrc(pack.getSegment(), pack.getCrc(), PacketElement.DATA_CRC);
        }
        pack.setFooter(parser.readFooter());

        return pack;
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setParserConfigurator(Configurator<T212Parser> parserConfigurator) {
        this.parserConfigurator = parserConfigurator;
    }

}
