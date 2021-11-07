package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车间对象 sys_workshop
 * 
 * @author ruoyi
 * @date 2021-11-07
 */
public class SysWorkshop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 车间名称 */
    @Excel(name = "车间名称")
    private String workshop;

    /** 关联企业id */
    @Excel(name = "关联企业id")
    private Long companyId;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWorkshop(String workshop) 
    {
        this.workshop = workshop;
    }

    public String getWorkshop() 
    {
        return workshop;
    }
    public void setCompanyId(Long companyId) 
    {
        this.companyId = companyId;
    }

    public Long getCompanyId() 
    {
        return companyId;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workshop", getWorkshop())
            .append("companyId", getCompanyId())
            .append("sort", getSort())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
