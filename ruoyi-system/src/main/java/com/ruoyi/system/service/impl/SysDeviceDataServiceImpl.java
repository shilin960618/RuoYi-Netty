package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysDeviceDataMapper;
import com.ruoyi.system.domain.SysDeviceData;
import com.ruoyi.system.service.ISysDeviceDataService;
import com.ruoyi.common.core.text.Convert;

/**
 * 回传信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
@Service
public class SysDeviceDataServiceImpl implements ISysDeviceDataService 
{
    @Autowired
    private SysDeviceDataMapper sysDeviceDataMapper;

    /**
     * 查询回传信息
     * 
     * @param id 回传信息主键
     * @return 回传信息
     */
    @Override
    public SysDeviceData selectSysDeviceDataById(Long id)
    {
        return sysDeviceDataMapper.selectSysDeviceDataById(id);
    }

    /**
     * 查询回传信息列表
     * 
     * @param sysDeviceData 回传信息
     * @return 回传信息
     */
    @Override
    public List<SysDeviceData> selectSysDeviceDataList(SysDeviceData sysDeviceData)
    {
        return sysDeviceDataMapper.selectSysDeviceDataList(sysDeviceData);
    }

    /**
     * 新增回传信息
     * 
     * @param sysDeviceData 回传信息
     * @return 结果
     */
    @Override
    public int insertSysDeviceData(SysDeviceData sysDeviceData)
    {
        return sysDeviceDataMapper.insertSysDeviceData(sysDeviceData);
    }

    /**
     * 修改回传信息
     * 
     * @param sysDeviceData 回传信息
     * @return 结果
     */
    @Override
    public int updateSysDeviceData(SysDeviceData sysDeviceData)
    {
        return sysDeviceDataMapper.updateSysDeviceData(sysDeviceData);
    }

    /**
     * 批量删除回传信息
     * 
     * @param ids 需要删除的回传信息主键
     * @return 结果
     */
    @Override
    public int deleteSysDeviceDataByIds(String ids)
    {
        return sysDeviceDataMapper.deleteSysDeviceDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除回传信息信息
     * 
     * @param id 回传信息主键
     * @return 结果
     */
    @Override
    public int deleteSysDeviceDataById(Long id)
    {
        return sysDeviceDataMapper.deleteSysDeviceDataById(id);
    }
}
