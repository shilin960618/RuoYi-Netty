package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysGateway;
import com.ruoyi.system.service.ISysGatewayService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 网关Controller
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
@Controller
@RequestMapping("/system/gateway")
public class SysGatewayController extends BaseController
{
    private String prefix = "system/gateway";

    @Autowired
    private ISysGatewayService sysGatewayService;

    @RequiresPermissions("system:gateway:view")
    @GetMapping()
    public String gateway()
    {
        return prefix + "/gateway";
    }

    /**
     * 查询网关列表
     */
    @RequiresPermissions("system:gateway:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysGateway sysGateway)
    {
        startPage();
        List<SysGateway> list = sysGatewayService.selectSysGatewayList(sysGateway);
        return getDataTable(list);
    }

    /**
     * 导出网关列表
     */
    @RequiresPermissions("system:gateway:export")
    @Log(title = "网关", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysGateway sysGateway)
    {
        List<SysGateway> list = sysGatewayService.selectSysGatewayList(sysGateway);
        ExcelUtil<SysGateway> util = new ExcelUtil<SysGateway>(SysGateway.class);
        return util.exportExcel(list, "网关数据");
    }

    /**
     * 新增网关
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存网关
     */
    @RequiresPermissions("system:gateway:add")
    @Log(title = "网关", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysGateway sysGateway)
    {
        return toAjax(sysGatewayService.insertSysGateway(sysGateway));
    }

    /**
     * 修改网关
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysGateway sysGateway = sysGatewayService.selectSysGatewayById(id);
        mmap.put("sysGateway", sysGateway);
        return prefix + "/edit";
    }

    /**
     * 修改保存网关
     */
    @RequiresPermissions("system:gateway:edit")
    @Log(title = "网关", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysGateway sysGateway)
    {
        return toAjax(sysGatewayService.updateSysGateway(sysGateway));
    }

    /**
     * 删除网关
     */
    @RequiresPermissions("system:gateway:remove")
    @Log(title = "网关", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysGatewayService.deleteSysGatewayByIds(ids));
    }
}
