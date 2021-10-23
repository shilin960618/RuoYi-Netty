package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysDeviceMapper;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.service.ISysDeviceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
@Service
public class SysDeviceServiceImpl implements ISysDeviceService 
{
    @Autowired
    private SysDeviceMapper sysDeviceMapper;

    /**
     * 查询设备
     * 
     * @param id 设备主键
     * @return 设备
     */
    @Override
    public SysDevice selectSysDeviceById(Long id)
    {
        return sysDeviceMapper.selectSysDeviceById(id);
    }

    /**
     * 查询设备列表
     * 
     * @param sysDevice 设备
     * @return 设备
     */
    @Override
    public List<SysDevice> selectSysDeviceList(SysDevice sysDevice)
    {
        return sysDeviceMapper.selectSysDeviceList(sysDevice);
    }

    /**
     * 新增设备
     * 
     * @param sysDevice 设备
     * @return 结果
     */
    @Override
    public int insertSysDevice(SysDevice sysDevice)
    {
        sysDevice.setCreateTime(DateUtils.getNowDate());
        return sysDeviceMapper.insertSysDevice(sysDevice);
    }

    /**
     * 修改设备
     * 
     * @param sysDevice 设备
     * @return 结果
     */
    @Override
    public int updateSysDevice(SysDevice sysDevice)
    {
        sysDevice.setUpdateTime(DateUtils.getNowDate());
        return sysDeviceMapper.updateSysDevice(sysDevice);
    }

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteSysDeviceByIds(String ids)
    {
        return sysDeviceMapper.deleteSysDeviceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备信息
     * 
     * @param id 设备主键
     * @return 结果
     */
    @Override
    public int deleteSysDeviceById(Long id)
    {
        return sysDeviceMapper.deleteSysDeviceById(id);
    }
}
