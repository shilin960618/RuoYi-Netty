package com.ruoyi.system.Dto;

import com.ruoyi.system.domain.SysCompany;

public class SysCompanyDto extends SysCompany {
    private String industryName;

    private String streetName;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return "SysCompanyDto{" +
                "industryName='" + industryName + '\'' +
                ", streetName='" + streetName + '\'' +
                '}';
    }
}
