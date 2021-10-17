package com.ruoyi.web.netty.hj212.parser.model.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.web.netty.hj212.parser.model.CpData;
import com.ruoyi.web.netty.hj212.parser.model.DataFlag;

import java.util.List;

/**
 * 混合
 * 序列化
 * @see Data 时使用
 * Created on 2017/12/19.
 */
public abstract class DataSerializationMixin {

    @JsonProperty("QN")
    abstract String getQn();

    @JsonProperty("PNUM")
    abstract int getpNum();

    @JsonProperty("PNO")
    abstract int getpNo();

    @JsonProperty("ST")
    abstract String getSt();

    @JsonProperty("CN")
    abstract String getCn();

    @JsonProperty("PW")
    abstract String getPw();

    @JsonProperty("MN")
    abstract String getMn();

    @JsonProperty("Flag")
    abstract List<DataFlag> getDataFlag();

    @JsonProperty("CP")
    abstract CpData getCp();

}
