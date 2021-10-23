package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysDeviceData;

/**
 * 回传信息Service接口
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
public interface ISysDeviceDataService 
{
    /**
     * 查询回传信息
     * 
     * @param id 回传信息主键
     * @return 回传信息
     */
    public SysDeviceData selectSysDeviceDataById(Long id);

    /**
     * 查询回传信息列表
     * 
     * @param sysDeviceData 回传信息
     * @return 回传信息集合
     */
    public List<SysDeviceData> selectSysDeviceDataList(SysDeviceData sysDeviceData);

    /**
     * 新增回传信息
     * 
     * @param sysDeviceData 回传信息
     * @return 结果
     */
    public int insertSysDeviceData(SysDeviceData sysDeviceData);

    /**
     * 修改回传信息
     * 
     * @param sysDeviceData 回传信息
     * @return 结果
     */
    public int updateSysDeviceData(SysDeviceData sysDeviceData);

    /**
     * 批量删除回传信息
     * 
     * @param ids 需要删除的回传信息主键集合
     * @return 结果
     */
    public int deleteSysDeviceDataByIds(String ids);

    /**
     * 删除回传信息信息
     * 
     * @param id 回传信息主键
     * @return 结果
     */
    public int deleteSysDeviceDataById(Long id);
}
