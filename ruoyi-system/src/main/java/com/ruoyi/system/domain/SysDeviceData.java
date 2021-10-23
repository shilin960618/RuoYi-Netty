package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 回传信息对象 sys_device_data
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
public class SysDeviceData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据表主键 */
    private Long id;

    /** 网关表的唯一识别编码 */
    @Excel(name = "网关表的唯一识别编码")
    private String uuid;

    /** 所属分组的名字 网关名字 */
    @Excel(name = "所属分组的名字 网关名字")
    private String groupName;

    /** 设备id */
    @Excel(name = "设备id")
    private Long deviceId;

    /** 设置名字 */
    @Excel(name = "设置名字")
    private String deviceName;

    /** 数据 */
    @Excel(name = "数据")
    private String result;

    /** 插入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "插入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creatTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUuid(String uuid) 
    {
        this.uuid = uuid;
    }

    public String getUuid() 
    {
        return uuid;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setCreatTime(Date creatTime) 
    {
        this.creatTime = creatTime;
    }

    public Date getCreatTime() 
    {
        return creatTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uuid", getUuid())
            .append("groupName", getGroupName())
            .append("deviceId", getDeviceId())
            .append("deviceName", getDeviceName())
            .append("result", getResult())
            .append("creatTime", getCreatTime())
            .toString();
    }
}
