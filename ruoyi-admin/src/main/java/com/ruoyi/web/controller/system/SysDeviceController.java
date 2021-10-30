package com.ruoyi.web.controller.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.service.ISysDeviceService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备Controller
 * 
 * @author ruoyi
 * @date 2021-10-23
 */
@Controller
@RequestMapping("/system/device")
public class SysDeviceController extends BaseController
{
    private String prefix = "system/device";

    @Autowired
    private ISysDeviceService sysDeviceService;

    @RequiresPermissions("system:device:view")
    @GetMapping()
    public String device()
    {
        return prefix + "/device";
    }

    /**
     * 查询设备列表
     */
    @RequiresPermissions("system:device:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDevice sysDevice)
    {
        startPage();
        List<SysDevice> list = sysDeviceService.selectSysDeviceList(sysDevice);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @RequiresPermissions("system:device:export")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDevice sysDevice) throws Exception {
        List<SysDevice> list = sysDeviceService.selectSysDeviceList(sysDevice);
        SXSSFWorkbook wb = new SXSSFWorkbook();
        try{
            Sheet sheet = wb.createSheet("设备数据");
            sheet.setColumnWidth(0, 8000);
            sheet.setColumnWidth(1, 8000);
            sheet.setColumnWidth(2, 8000);
            sheet.setColumnWidth(3, 8000);
            sheet.setColumnWidth(4, 8000);
            sheet.setColumnWidth(5, 8000);
            sheet.setColumnWidth(6, 8000);
            Row row2 = sheet.createRow(0);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("设备名称");
            row2.createCell(1).setCellValue("网关ID");
            row2.createCell(2).setCellValue("设备ID");
            row2.createCell(3).setCellValue("阈值");
            row2.createCell(4).setCellValue("最后一次数据");
            row2.createCell(5).setCellValue("是否在线");
            row2.createCell(6).setCellValue("最后回传时间");
            Integer index = 1;
            for (SysDevice device : list) {
                Row row = sheet.createRow(index);
                row.createCell(0).setCellValue(device.getDeviceName());
                row.createCell(1).setCellValue(device.getUuid());
                row.createCell(2).setCellValue(device.getNodeId());
                row.createCell(3).setCellValue(device.getThreshold());
                row.createCell(4).setCellValue(device.getData());
                if (StringUtils.isNotEmpty(device.getIsOnline()) && device.getIsOnline().equals("1")) {
                    row.createCell(5).setCellValue("在线");
                } else {
                    row.createCell(5).setCellValue("离线");
                }
                row.createCell(6).setCellValue(device.getSendTime());
                index++;
            }
        }catch (Exception e){
            throw new Exception("导出失败");
        }
        FileOutputStream fos = null;
        String absoluteFile = "";
        String name = "回传";
        try {
            absoluteFile = ExcelUtil.getAbsoluteFile(name + "-" + "设备数据"+ "-"+ DateUtils.getDate()+".xlsx");
            fos = new FileOutputStream(absoluteFile);
            wb.write(fos);
            return AjaxResult.success(name + "-" + "设备数据"+ "-"+ DateUtils.getDate()+".xlsx");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            wb.close();
            fos.close();
        }
        return null;
    }

    /**
     * 新增设备
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备
     */
    @RequiresPermissions("system:device:add")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDevice sysDevice)
    {
        return toAjax(sysDeviceService.insertSysDevice(sysDevice));
    }

    /**
     * 修改设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysDevice sysDevice = sysDeviceService.selectSysDeviceById(id);
        mmap.put("sysDevice", sysDevice);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备
     */
    @RequiresPermissions("system:device:edit")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDevice sysDevice)
    {
        return toAjax(sysDeviceService.updateSysDevice(sysDevice));
    }

    /**
     * 删除设备
     */
    @RequiresPermissions("system:device:remove")
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysDeviceService.deleteSysDeviceByIds(ids));
    }
}
