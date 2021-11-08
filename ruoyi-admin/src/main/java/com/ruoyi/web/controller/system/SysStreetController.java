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
import com.ruoyi.system.domain.SysStreet;
import com.ruoyi.system.service.ISysStreetService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 地区Controller
 * 
 * @author ruoyi
 * @date 2021-11-08
 */
@Controller
@RequestMapping("/system/street")
public class SysStreetController extends BaseController
{
    private String prefix = "system/street";

    @Autowired
    private ISysStreetService sysStreetService;

    @RequiresPermissions("system:street:view")
    @GetMapping()
    public String street()
    {
        return prefix + "/street";
    }

    /**
     * 查询地区列表
     */
    @RequiresPermissions("system:street:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysStreet sysStreet)
    {
        startPage();
        List<SysStreet> list = sysStreetService.selectSysStreetList(sysStreet);
        return getDataTable(list);
    }

    /**
     * 导出地区列表
     */
    @RequiresPermissions("system:street:export")
    @Log(title = "地区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysStreet sysStreet)
    {
        List<SysStreet> list = sysStreetService.selectSysStreetList(sysStreet);
        ExcelUtil<SysStreet> util = new ExcelUtil<SysStreet>(SysStreet.class);
        return util.exportExcel(list, "地区数据");
    }

    /**
     * 新增地区
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存地区
     */
    @RequiresPermissions("system:street:add")
    @Log(title = "地区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysStreet sysStreet)
    {
        return toAjax(sysStreetService.insertSysStreet(sysStreet));
    }

    /**
     * 修改地区
     */
    @GetMapping("/edit/{streetId}")
    public String edit(@PathVariable("streetId") Long streetId, ModelMap mmap)
    {
        SysStreet sysStreet = sysStreetService.selectSysStreetByStreetId(streetId);
        mmap.put("sysStreet", sysStreet);
        return prefix + "/edit";
    }

    /**
     * 修改保存地区
     */
    @RequiresPermissions("system:street:edit")
    @Log(title = "地区", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysStreet sysStreet)
    {
        return toAjax(sysStreetService.updateSysStreet(sysStreet));
    }

    /**
     * 删除地区
     */
    @RequiresPermissions("system:street:remove")
    @Log(title = "地区", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysStreetService.deleteSysStreetByStreetIds(ids));
    }
}
