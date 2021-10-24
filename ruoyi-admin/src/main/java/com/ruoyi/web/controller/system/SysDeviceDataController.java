package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.Dto.DeviceDataDto;
import com.ruoyi.system.vo.DataVo;
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
import com.ruoyi.system.domain.SysDeviceData;
import com.ruoyi.system.service.ISysDeviceDataService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 回传信息Controller
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
@Controller
@RequestMapping("/system/data")
public class SysDeviceDataController extends BaseController
{
    private String prefix = "system/data";

    @Autowired
    private ISysDeviceDataService sysDeviceDataService;

    @RequiresPermissions("system:data:view")
    @GetMapping()
    public String data()
    {
        return prefix + "/data";
    }

    /**
     * 查询回传信息列表
     */
    @RequiresPermissions("system:data:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDeviceData sysDeviceData)
    {
        startPage();
        List<SysDeviceData> list = sysDeviceDataService.selectSysDeviceDataList(sysDeviceData);
        List<DeviceDataDto> deviceDataDtos = new ArrayList<>();
        for (SysDeviceData sysDeviceDataRes:list) {
            DeviceDataDto deviceDataDto = new DeviceDataDto();
            DataVo dataVo = JSON.parseObject(sysDeviceDataRes.getResult(), DataVo.class);
            BeanUtils.copyBeanProp(deviceDataDto,sysDeviceDataRes);
            BeanUtils.copyBeanProp(deviceDataDto,dataVo);
            deviceDataDtos.add(deviceDataDto);
        }
        return getDataTable(deviceDataDtos);
    }

    /**
     * 导出回传信息列表
     */
    @RequiresPermissions("system:data:export")
    @Log(title = "回传信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDeviceData sysDeviceData)
    {
        List<SysDeviceData> list = sysDeviceDataService.selectSysDeviceDataList(sysDeviceData);
        ExcelUtil<SysDeviceData> util = new ExcelUtil<SysDeviceData>(SysDeviceData.class);
        return util.exportExcel(list, "回传信息数据");
    }

    /**
     * 新增回传信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存回传信息
     */
    @RequiresPermissions("system:data:add")
    @Log(title = "回传信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDeviceData sysDeviceData)
    {
        return toAjax(sysDeviceDataService.insertSysDeviceData(sysDeviceData));
    }

    /**
     * 修改回传信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysDeviceData sysDeviceData = sysDeviceDataService.selectSysDeviceDataById(id);
        mmap.put("sysDeviceData", sysDeviceData);
        return prefix + "/edit";
    }

    /**
     * 修改保存回传信息
     */
    @RequiresPermissions("system:data:edit")
    @Log(title = "回传信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDeviceData sysDeviceData)
    {
        return toAjax(sysDeviceDataService.updateSysDeviceData(sysDeviceData));
    }

    /**
     * 删除回传信息
     */
    @RequiresPermissions("system:data:remove")
    @Log(title = "回传信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysDeviceDataService.deleteSysDeviceDataByIds(ids));
    }
}
