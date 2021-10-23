package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysGateway;

/**
 * 网关Mapper接口
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
public interface SysGatewayMapper 
{
    /**
     * 查询网关
     * 
     * @param id 网关主键
     * @return 网关
     */
    public SysGateway selectSysGatewayById(Long id);

    /**
     * 查询网关列表
     * 
     * @param sysGateway 网关
     * @return 网关集合
     */
    public List<SysGateway> selectSysGatewayList(SysGateway sysGateway);

    /**
     * 新增网关
     * 
     * @param sysGateway 网关
     * @return 结果
     */
    public int insertSysGateway(SysGateway sysGateway);

    /**
     * 修改网关
     * 
     * @param sysGateway 网关
     * @return 结果
     */
    public int updateSysGateway(SysGateway sysGateway);

    /**
     * 删除网关
     * 
     * @param id 网关主键
     * @return 结果
     */
    public int deleteSysGatewayById(Long id);

    /**
     * 批量删除网关
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGatewayByIds(String[] ids);
}
