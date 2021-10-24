package com.ruoyi.system.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.util.Date;

public class DeviceDataDto {
    /** 数据表主键 */
    private Long id;

    /** 网关表的唯一识别编码 */
    private String uuid;

    /** 所属分组的名字 网关名字 */
    private String groupName;

    /** 设备id */

    private Long deviceId;

    /** 设置名字 */

    private String deviceName;

    /** 数据 */
    private String result;

    /** 插入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private Date creatTime;

    // a项电压
    private String av;
    // b项电压
    private String bv;
    // c项电压
    private String cv;
    // a项电流
    private String ai;
    // b项电流
    private String bi;
    // c项电流
    private String ci;
    // 有功功率
    private String aw;
    // 无功功率
    private String bw;
    // 功率因数
    private String cw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getAi() {
        return ai;
    }

    public void setAi(String ai) {
        this.ai = ai;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getAw() {
        return aw;
    }

    public void setAw(String aw) {
        this.aw = aw;
    }

    public String getBw() {
        return bw;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    public String getCw() {
        return cw;
    }

    public void setCw(String cw) {
        this.cw = cw;
    }

    @Override
    public String toString() {
        return "DeviceDataDto{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", groupName='" + groupName + '\'' +
                ", deviceId=" + deviceId +
                ", deviceName='" + deviceName + '\'' +
                ", result='" + result + '\'' +
                ", creatTime=" + creatTime +
                ", av='" + av + '\'' +
                ", bv='" + bv + '\'' +
                ", cv='" + cv + '\'' +
                ", ai='" + ai + '\'' +
                ", bi='" + bi + '\'' +
                ", ci='" + ci + '\'' +
                ", aw='" + aw + '\'' +
                ", bw='" + bw + '\'' +
                ", cw='" + cw + '\'' +
                '}';
    }
}
