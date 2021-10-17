package com.ruoyi.web.netty.hj212.segmentParser.core.ser;



import com.ruoyi.web.netty.hj212.segmentParser.core.SegmentGenerator;
import com.ruoyi.web.netty.hj212.segmentParser.exception.SegmentFormatException;

import java.io.IOException;
import java.util.Map;

/**
 * Created on 2017/12/15.
 */
public class StringMapSegmentSerializer
        implements SegmentSerializer<Map<String,String>> {

    @Override
    public void serialize(SegmentGenerator generator, Map<String, String> data) throws IOException, SegmentFormatException {
        if(generator.nextToken() == null){
            generator.initToken();
        }
        writeMap(generator,data);
    }

    private void writeMap(SegmentGenerator generator, Map<String, String> result) throws IOException, SegmentFormatException {
        for (Map.Entry<String, String> kv : result.entrySet()) {
            generator.writeKey(kv.getKey());
            generator.writeValue(kv.getValue());
        }
    }

}
