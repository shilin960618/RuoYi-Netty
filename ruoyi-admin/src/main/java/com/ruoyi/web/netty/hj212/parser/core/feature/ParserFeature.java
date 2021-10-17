package com.ruoyi.web.netty.hj212.parser.core.feature;


import com.ruoyi.web.netty.hj212.segmentParser.cfger.Feature;

/**
 * 解析特性
 * Created on 2018/1/3.
 */
public enum ParserFeature implements Feature {

    /**
     * 头常量
     */
    HEADER_CONSTANT(true),

    /**
     * 尾常量
     */
    FOOTER_CONSTANT(false);


    private final boolean _defaultState;
    private final int _mask;

    ParserFeature(boolean defaultState) {
        _defaultState = defaultState;
        _mask = (1 << ordinal());
    }

    @Override
    public boolean enabledByDefault() { return _defaultState; }

    @Override
    public int getMask() { return _mask; }

    @Override
    public boolean enabledIn(int flags) { return (flags & _mask) != 0; }

}
