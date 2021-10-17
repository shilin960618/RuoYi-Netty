package com.ruoyi.web.netty.hj212.parser.model.mixin;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.web.netty.hj212.parser.model.DataFlag;

import java.util.List;

/**
 * 混合
 * 解决Map转为
 * @see Data 时剔除的字段
 * Created on 2017/12/19.
 */
@Deprecated
public abstract class DataMixin {

    @JsonIgnore
    abstract String getDataFlag();

    @JsonIgnore
    abstract void setDataFlag(List<DataFlag> dataFlag);

    @JsonIgnore
    abstract String getCp();

    @JsonIgnore
    abstract void setCp(String cp);
}
