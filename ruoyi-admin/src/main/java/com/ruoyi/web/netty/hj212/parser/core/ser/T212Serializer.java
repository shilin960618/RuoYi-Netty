package com.ruoyi.web.netty.hj212.parser.core.ser;


import com.ruoyi.web.netty.hj212.parser.core.T212Generator;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;

import java.io.IOException;

/**
 * T212序列化器
 * Created on 2018/2/24.
 */
public interface T212Serializer<Target> {

    void serialize(T212Generator generator, Target target) throws IOException, T212FormatException;
}
