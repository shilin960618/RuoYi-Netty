package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysStreetMapper;
import com.ruoyi.system.domain.SysStreet;
import com.ruoyi.system.service.ISysStreetService;
import com.ruoyi.common.core.text.Convert;

/**
 * 地区Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-11-08
 */
@Service
public class SysStreetServiceImpl implements ISysStreetService 
{
    @Autowired
    private SysStreetMapper sysStreetMapper;

    /**
     * 查询地区
     * 
     * @param streetId 地区主键
     * @return 地区
     */
    @Override
    public SysStreet selectSysStreetByStreetId(Long streetId)
    {
        return sysStreetMapper.selectSysStreetByStreetId(streetId);
    }

    /**
     * 查询地区列表
     * 
     * @param sysStreet 地区
     * @return 地区
     */
    @Override
    public List<SysStreet> selectSysStreetList(SysStreet sysStreet)
    {
        return sysStreetMapper.selectSysStreetList(sysStreet);
    }

    /**
     * 新增地区
     * 
     * @param sysStreet 地区
     * @return 结果
     */
    @Override
    public int insertSysStreet(SysStreet sysStreet)
    {
        sysStreet.setCreateTime(DateUtils.getNowDate());
        return sysStreetMapper.insertSysStreet(sysStreet);
    }

    /**
     * 修改地区
     * 
     * @param sysStreet 地区
     * @return 结果
     */
    @Override
    public int updateSysStreet(SysStreet sysStreet)
    {
        return sysStreetMapper.updateSysStreet(sysStreet);
    }

    /**
     * 批量删除地区
     * 
     * @param streetIds 需要删除的地区主键
     * @return 结果
     */
    @Override
    public int deleteSysStreetByStreetIds(String streetIds)
    {
        return sysStreetMapper.deleteSysStreetByStreetIds(Convert.toStrArray(streetIds));
    }

    /**
     * 删除地区信息
     * 
     * @param streetId 地区主键
     * @return 结果
     */
    @Override
    public int deleteSysStreetByStreetId(Long streetId)
    {
        return sysStreetMapper.deleteSysStreetByStreetId(streetId);
    }
}
