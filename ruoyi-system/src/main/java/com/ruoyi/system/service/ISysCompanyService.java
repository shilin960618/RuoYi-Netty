package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysCompany;

/**
 * 企业Service接口
 * 
 * @author ruoyi
 * @date 2021-11-03
 */
public interface ISysCompanyService 
{
    /**
     * 查询企业
     * 
     * @param id 企业主键
     * @return 企业
     */
    public SysCompany selectSysCompanyById(Long id);

    /**
     * 查询企业列表
     * 
     * @param sysCompany 企业
     * @return 企业集合
     */
    public List<SysCompany> selectSysCompanyList(SysCompany sysCompany);

    /**
     * 新增企业
     * 
     * @param sysCompany 企业
     * @return 结果
     */
    public int insertSysCompany(SysCompany sysCompany);

    /**
     * 修改企业
     * 
     * @param sysCompany 企业
     * @return 结果
     */
    public int updateSysCompany(SysCompany sysCompany);

    /**
     * 批量删除企业
     * 
     * @param ids 需要删除的企业主键集合
     * @return 结果
     */
    public int deleteSysCompanyByIds(String ids);

    /**
     * 删除企业信息
     * 
     * @param id 企业主键
     * @return 结果
     */
    public int deleteSysCompanyById(Long id);
}
