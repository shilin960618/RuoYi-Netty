package com.ruoyi.web.netty.hj212.parser.base;

/**
 * Created on 2018/1/3.
 */
public interface Converter<SRC,TAR> {

    TAR convert(SRC src) throws Exception;
}
