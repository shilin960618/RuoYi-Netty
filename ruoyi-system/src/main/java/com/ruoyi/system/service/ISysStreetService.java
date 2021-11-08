package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysStreet;

/**
 * 地区Service接口
 * 
 * @author ruoyi
 * @date 2021-11-08
 */
public interface ISysStreetService 
{
    /**
     * 查询地区
     * 
     * @param streetId 地区主键
     * @return 地区
     */
    public SysStreet selectSysStreetByStreetId(Long streetId);

    /**
     * 查询地区列表
     * 
     * @param sysStreet 地区
     * @return 地区集合
     */
    public List<SysStreet> selectSysStreetList(SysStreet sysStreet);

    /**
     * 新增地区
     * 
     * @param sysStreet 地区
     * @return 结果
     */
    public int insertSysStreet(SysStreet sysStreet);

    /**
     * 修改地区
     * 
     * @param sysStreet 地区
     * @return 结果
     */
    public int updateSysStreet(SysStreet sysStreet);

    /**
     * 批量删除地区
     * 
     * @param streetIds 需要删除的地区主键集合
     * @return 结果
     */
    public int deleteSysStreetByStreetIds(String streetIds);

    /**
     * 删除地区信息
     * 
     * @param streetId 地区主键
     * @return 结果
     */
    public int deleteSysStreetByStreetId(Long streetId);
}
