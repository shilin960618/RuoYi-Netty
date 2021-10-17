package com.ruoyi.web.netty.hj212.parser.base.parser;



import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;

import java.io.Closeable;
import java.io.IOException;

/**
 * T212解析器
 * Created on 2018/1/3.
 */
public interface Parser<Target>
        extends Closeable {

    Target parse() throws T212FormatException, IOException;
}
