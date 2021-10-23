package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysDevice;

/**
 * 设备Service接口
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
public interface ISysDeviceService 
{
    /**
     * 查询设备
     * 
     * @param id 设备主键
     * @return 设备
     */
    public SysDevice selectSysDeviceById(Long id);

    /**
     * 查询设备列表
     * 
     * @param sysDevice 设备
     * @return 设备集合
     */
    public List<SysDevice> selectSysDeviceList(SysDevice sysDevice);

    /**
     * 新增设备
     * 
     * @param sysDevice 设备
     * @return 结果
     */
    public int insertSysDevice(SysDevice sysDevice);

    /**
     * 修改设备
     * 
     * @param sysDevice 设备
     * @return 结果
     */
    public int updateSysDevice(SysDevice sysDevice);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的设备主键集合
     * @return 结果
     */
    public int deleteSysDeviceByIds(String ids);

    /**
     * 删除设备信息
     * 
     * @param id 设备主键
     * @return 结果
     */
    public int deleteSysDeviceById(Long id);
}
