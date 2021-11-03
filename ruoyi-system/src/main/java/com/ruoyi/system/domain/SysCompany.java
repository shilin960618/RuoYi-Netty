package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业对象 sys_company
 * 
 * @author ruoyi
 * @date 2021-11-03
 */
public class SysCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String companyName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String companyCode;

    /** 关联街道 */
    @Excel(name = "关联街道")
    private Long streetId;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 行业分类 */
    @Excel(name = "行业分类")
    private Long industry;

    /** 法人 */
    @Excel(name = "法人")
    private String legalPerson;

    /** 联系手机 */
    @Excel(name = "联系手机")
    private String mobilePhone;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 环保负责人 */
    @Excel(name = "环保负责人")
    private String principal;

    /** 是否短信 */
    @Excel(name = "是否短信")
    private String isSms;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** mn码 */
    @Excel(name = "mn码")
    private String mnCode;

    /** 网关设备号 */
    @Excel(name = "网关设备号")
    private String wayCode;

    /** 网关频道号 */
    @Excel(name = "网关频道号")
    private String wayCodeChannel;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setCompanyCode(String companyCode) 
    {
        this.companyCode = companyCode;
    }

    public String getCompanyCode() 
    {
        return companyCode;
    }
    public void setStreetId(Long streetId) 
    {
        this.streetId = streetId;
    }

    public Long getStreetId() 
    {
        return streetId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setIndustry(Long industry) 
    {
        this.industry = industry;
    }

    public Long getIndustry() 
    {
        return industry;
    }
    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
    }
    public void setMobilePhone(String mobilePhone) 
    {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone() 
    {
        return mobilePhone;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setPrincipal(String principal) 
    {
        this.principal = principal;
    }

    public String getPrincipal() 
    {
        return principal;
    }
    public void setIsSms(String isSms) 
    {
        this.isSms = isSms;
    }

    public String getIsSms() 
    {
        return isSms;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setMnCode(String mnCode) 
    {
        this.mnCode = mnCode;
    }

    public String getMnCode() 
    {
        return mnCode;
    }
    public void setWayCode(String wayCode) 
    {
        this.wayCode = wayCode;
    }

    public String getWayCode() 
    {
        return wayCode;
    }
    public void setWayCodeChannel(String wayCodeChannel) 
    {
        this.wayCodeChannel = wayCodeChannel;
    }

    public String getWayCodeChannel() 
    {
        return wayCodeChannel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyName", getCompanyName())
            .append("companyCode", getCompanyCode())
            .append("streetId", getStreetId())
            .append("address", getAddress())
            .append("industry", getIndustry())
            .append("legalPerson", getLegalPerson())
            .append("mobilePhone", getMobilePhone())
            .append("phone", getPhone())
            .append("principal", getPrincipal())
            .append("isSms", getIsSms())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("remark", getRemark())
            .append("mnCode", getMnCode())
            .append("wayCode", getWayCode())
            .append("wayCodeChannel", getWayCodeChannel())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
