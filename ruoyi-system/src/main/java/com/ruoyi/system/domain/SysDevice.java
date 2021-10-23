package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备对象 sys_device
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
public class SysDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 网关uuid */
    @Excel(name = "网关uuid")
    private String uuid;

    /** 节点id */
    @Excel(name = "节点id")
    private Long nodeId;

    /** 阈值 */
    @Excel(name = "阈值")
    private String threshold;

    /** 最后一次数据 */
    @Excel(name = "最后一次数据")
    private String data;

    /** 关联用户id */
    @Excel(name = "关联用户id")
    private String users;

    /** 是否在线 */
    @Excel(name = "是否在线")
    private String isOnline;

    /** 本次报警是否已经发送报警短信，默认0 */
    @Excel(name = "本次报警是否已经发送报警短信，默认0")
    private String isSend;

    /** 最后回传时间 */
    @Excel(name = "最后回传时间")
    private String sendTime;

    /** 开关名称 */
    @Excel(name = "开关名称")
    private String switchName;

    /** 电量 */
    @Excel(name = "电量")
    private Long electricity;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setUuid(String uuid) 
    {
        this.uuid = uuid;
    }

    public String getUuid() 
    {
        return uuid;
    }
    public void setNodeId(Long nodeId) 
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId() 
    {
        return nodeId;
    }
    public void setThreshold(String threshold) 
    {
        this.threshold = threshold;
    }

    public String getThreshold() 
    {
        return threshold;
    }
    public void setData(String data) 
    {
        this.data = data;
    }

    public String getData() 
    {
        return data;
    }
    public void setUsers(String users) 
    {
        this.users = users;
    }

    public String getUsers() 
    {
        return users;
    }
    public void setIsOnline(String isOnline) 
    {
        this.isOnline = isOnline;
    }

    public String getIsOnline() 
    {
        return isOnline;
    }
    public void setIsSend(String isSend) 
    {
        this.isSend = isSend;
    }

    public String getIsSend() 
    {
        return isSend;
    }
    public void setSendTime(String sendTime) 
    {
        this.sendTime = sendTime;
    }

    public String getSendTime() 
    {
        return sendTime;
    }
    public void setSwitchName(String switchName) 
    {
        this.switchName = switchName;
    }

    public String getSwitchName() 
    {
        return switchName;
    }
    public void setElectricity(Long electricity) 
    {
        this.electricity = electricity;
    }

    public Long getElectricity() 
    {
        return electricity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceName", getDeviceName())
            .append("uuid", getUuid())
            .append("nodeId", getNodeId())
            .append("threshold", getThreshold())
            .append("data", getData())
            .append("users", getUsers())
            .append("isOnline", getIsOnline())
            .append("isSend", getIsSend())
            .append("sendTime", getSendTime())
            .append("switchName", getSwitchName())
            .append("electricity", getElectricity())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
