package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysGatewayMapper;
import com.ruoyi.system.domain.SysGateway;
import com.ruoyi.system.service.ISysGatewayService;
import com.ruoyi.common.core.text.Convert;

/**
 * 网关Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
@Service
public class SysGatewayServiceImpl implements ISysGatewayService 
{
    @Autowired
    private SysGatewayMapper sysGatewayMapper;

    /**
     * 查询网关
     * 
     * @param id 网关主键
     * @return 网关
     */
    @Override
    public SysGateway selectSysGatewayById(Long id)
    {
        return sysGatewayMapper.selectSysGatewayById(id);
    }

    /**
     * 查询网关列表
     * 
     * @param sysGateway 网关
     * @return 网关
     */
    @Override
    public List<SysGateway> selectSysGatewayList(SysGateway sysGateway)
    {
        return sysGatewayMapper.selectSysGatewayList(sysGateway);
    }

    /**
     * 新增网关
     * 
     * @param sysGateway 网关
     * @return 结果
     */
    @Override
    public int insertSysGateway(SysGateway sysGateway)
    {
        sysGateway.setCreateTime(DateUtils.getNowDate());
        return sysGatewayMapper.insertSysGateway(sysGateway);
    }

    /**
     * 修改网关
     * 
     * @param sysGateway 网关
     * @return 结果
     */
    @Override
    public int updateSysGateway(SysGateway sysGateway)
    {
        sysGateway.setUpdateTime(DateUtils.getNowDate());
        return sysGatewayMapper.updateSysGateway(sysGateway);
    }

    /**
     * 批量删除网关
     * 
     * @param ids 需要删除的网关主键
     * @return 结果
     */
    @Override
    public int deleteSysGatewayByIds(String ids)
    {
        return sysGatewayMapper.deleteSysGatewayByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除网关信息
     * 
     * @param id 网关主键
     * @return 结果
     */
    @Override
    public int deleteSysGatewayById(Long id)
    {
        return sysGatewayMapper.deleteSysGatewayById(id);
    }
}
