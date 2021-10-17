package com.ruoyi.web.netty.hj212.segmentParser.core.deser;



import com.ruoyi.web.netty.hj212.segmentParser.core.SegmentParser;
import com.ruoyi.web.netty.hj212.segmentParser.exception.SegmentFormatException;

import java.io.IOException;

/**
 * Created on 2018/1/4.
 */
public interface SegmentDeserializer<Target> {

    Target deserialize(SegmentParser parser) throws IOException, SegmentFormatException;
}
