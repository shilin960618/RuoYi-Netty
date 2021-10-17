package com.ruoyi.web.netty.hj212.parser.core.deser;



import com.ruoyi.web.netty.hj212.parser.core.T212Parser;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;

import java.io.IOException;

/**
 * T212反序列化器
 * Created on 2018/1/4.
 */
public interface T212Deserializer<Target> {

    Target deserialize(T212Parser parser) throws IOException, T212FormatException;
}
