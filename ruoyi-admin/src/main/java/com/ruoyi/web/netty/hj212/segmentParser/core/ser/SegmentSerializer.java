package com.ruoyi.web.netty.hj212.segmentParser.core.ser;



import com.ruoyi.web.netty.hj212.segmentParser.core.SegmentGenerator;
import com.ruoyi.web.netty.hj212.segmentParser.exception.SegmentFormatException;

import java.io.IOException;

/**
 * Created on 2018/2/24.
 */
public interface SegmentSerializer<Target> {

    void serialize(SegmentGenerator generator, Target target) throws IOException, SegmentFormatException;
}
