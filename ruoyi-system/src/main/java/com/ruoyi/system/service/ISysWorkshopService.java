package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysWorkshop;

/**
 * 车间Service接口
 * 
 * @author ruoyi
 * @date 2021-11-07
 */
public interface ISysWorkshopService 
{
    /**
     * 查询车间
     * 
     * @param id 车间主键
     * @return 车间
     */
    public SysWorkshop selectSysWorkshopById(Long id);

    /**
     * 查询车间列表
     * 
     * @param sysWorkshop 车间
     * @return 车间集合
     */
    public List<SysWorkshop> selectSysWorkshopList(SysWorkshop sysWorkshop);

    /**
     * 新增车间
     * 
     * @param sysWorkshop 车间
     * @return 结果
     */
    public int insertSysWorkshop(SysWorkshop sysWorkshop);

    /**
     * 修改车间
     * 
     * @param sysWorkshop 车间
     * @return 结果
     */
    public int updateSysWorkshop(SysWorkshop sysWorkshop);

    /**
     * 批量删除车间
     * 
     * @param ids 需要删除的车间主键集合
     * @return 结果
     */
    public int deleteSysWorkshopByIds(String ids);

    /**
     * 删除车间信息
     * 
     * @param id 车间主键
     * @return 结果
     */
    public int deleteSysWorkshopById(Long id);
}
