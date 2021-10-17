package com.ruoyi.web.netty.hj212.parser.model.verify.groups;

/**
 * 模式 验证组
 * Created on 2018/1/10.
 */
public interface ModeGroup {
    /**
     * 严格模式
     */
    @Deprecated
    interface Strict{}

    /**
     * 分包模式
     */
    interface UseSubPacket{}
}
