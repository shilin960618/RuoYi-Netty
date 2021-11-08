package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.domain.SysStreet;
import org.apache.ibatis.annotations.Param;

/**
 * 地区Mapper接口
 * 
 * @author ruoyi
 * @date 2021-11-08
 */
public interface SysStreetMapper 
{
    /**
     * 查询地区
     *
     * @param street 地区
     * @return 结果
     */
    public int selectStreetCount(SysStreet street);

    /**
     * 查询地区是否存在企业
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkStreetExistCompany(Long deptId);

    /**
     * 查询地区
     * 
     * @param streetId 地区主键
     * @return 地区
     */
    public SysStreet selectSysStreetByStreetId(Long streetId);

    /**
     * 查询地区列表
     * 
     * @param sysStreet 地区
     * @return 地区集合
     */
    public List<SysStreet> selectSysStreetList(SysStreet sysStreet);

    /**
     * 新增地区
     * 
     * @param sysStreet 地区
     * @return 结果
     */
    public int insertSysStreet(SysStreet sysStreet);

    /**
     * 修改地区
     * 
     * @param sysStreet 地区
     * @return 结果
     */
    public int updateSysStreet(SysStreet sysStreet);

    /**
     * 删除地区
     * 
     * @param streetId 地区主键
     * @return 结果
     */
    public int deleteSysStreetByStreetId(Long streetId);

    /**
     * 批量删除地区
     * 
     * @param streetIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysStreetByStreetIds(String[] streetIds);

    /**
     * 修改子元素关系
     *
     * @param streets 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysStreet> streets);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysStreet selectDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysStreet checkStreetNameUnique(@Param("streetName") String deptName, @Param("parentId") Long parentId);

    /**
     *
     *
     * @param roleId 角色ID
     * @return 地区列表
     */
    public List<String> selectRoleStreetTree(Long roleId);

    /**
     * 修改所在地区正常状态
     *
     * @param streetIds 部门ID组
     */
    public void updateStreetStatusNormal(Long[] streetIds);

    /**
     * 根据ID查询所有子地区
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SysStreet> selectChildrenStreetById(Long deptId);

    /**
     * 根据ID查询所有子地区（正常状态）
     *
     * @param deptId 部门ID
     * @return 子地区数
     */
    public int selectNormalChildrenStreetById(Long deptId);
}
