package com.ruoyi.web.netty.hj212.segmentParser.core.feature;


import com.ruoyi.web.netty.hj212.segmentParser.cfger.Feature;

/**
 * Created on 2018/2/24.
 */
@Deprecated
public enum SegmentGeneratorFeature implements Feature {

    /**
     * 未使用
     */
    NOT_USE(true);

    /**
     * 允许NULL KEY闭合Object value
     */
//    ALLOW_NULL_KEY_CLOSE_OBJECT_VALUE(true);


    private final boolean _defaultState;
    private final int _mask;

    SegmentGeneratorFeature(boolean defaultState) {
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
