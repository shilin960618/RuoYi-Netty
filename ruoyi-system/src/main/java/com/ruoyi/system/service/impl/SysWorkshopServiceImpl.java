package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysWorkshopMapper;
import com.ruoyi.system.domain.SysWorkshop;
import com.ruoyi.system.service.ISysWorkshopService;
import com.ruoyi.common.core.text.Convert;

/**
 * 车间Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-11-07
 */
@Service
public class SysWorkshopServiceImpl implements ISysWorkshopService 
{
    @Autowired
    private SysWorkshopMapper sysWorkshopMapper;

    /**
     * 查询车间
     * 
     * @param id 车间主键
     * @return 车间
     */
    @Override
    public SysWorkshop selectSysWorkshopById(Long id)
    {
        return sysWorkshopMapper.selectSysWorkshopById(id);
    }

    /**
     * 查询车间列表
     * 
     * @param sysWorkshop 车间
     * @return 车间
     */
    @Override
    public List<SysWorkshop> selectSysWorkshopList(SysWorkshop sysWorkshop)
    {
        return sysWorkshopMapper.selectSysWorkshopList(sysWorkshop);
    }

    /**
     * 新增车间
     * 
     * @param sysWorkshop 车间
     * @return 结果
     */
    @Override
    public int insertSysWorkshop(SysWorkshop sysWorkshop)
    {
        sysWorkshop.setCreateTime(DateUtils.getNowDate());
        return sysWorkshopMapper.insertSysWorkshop(sysWorkshop);
    }

    /**
     * 修改车间
     * 
     * @param sysWorkshop 车间
     * @return 结果
     */
    @Override
    public int updateSysWorkshop(SysWorkshop sysWorkshop)
    {
        sysWorkshop.setUpdateTime(DateUtils.getNowDate());
        return sysWorkshopMapper.updateSysWorkshop(sysWorkshop);
    }

    /**
     * 批量删除车间
     * 
     * @param ids 需要删除的车间主键
     * @return 结果
     */
    @Override
    public int deleteSysWorkshopByIds(String ids)
    {
        return sysWorkshopMapper.deleteSysWorkshopByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除车间信息
     * 
     * @param id 车间主键
     * @return 结果
     */
    @Override
    public int deleteSysWorkshopById(Long id)
    {
        return sysWorkshopMapper.deleteSysWorkshopById(id);
    }
}
