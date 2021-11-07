package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysWorkshop;

/**
 * 车间Mapper接口
 * 
 * @author ruoyi
 * @date 2021-11-07
 */
public interface SysWorkshopMapper 
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
     * 删除车间
     * 
     * @param id 车间主键
     * @return 结果
     */
    public int deleteSysWorkshopById(Long id);

    /**
     * 批量删除车间
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysWorkshopByIds(String[] ids);
}
