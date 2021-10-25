package com.ruoyi.web.controller.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.Dto.DeviceDataDto;
import com.ruoyi.system.vo.DataVo;
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
        TableDataInfo dataTableInfo = getDataTable(list);
        List<DeviceDataDto> deviceDataDtos = new ArrayList<>();
        for (SysDeviceData sysDeviceDataRes:list) {
            DeviceDataDto deviceDataDto = new DeviceDataDto();
            DataVo dataVo = JSON.parseObject(sysDeviceDataRes.getResult(), DataVo.class);
            BeanUtils.copyBeanProp(deviceDataDto,sysDeviceDataRes);
            BeanUtils.copyBeanProp(deviceDataDto,dataVo);
            deviceDataDtos.add(deviceDataDto);
        }
        dataTableInfo.setRows(deviceDataDtos);
        return dataTableInfo;
    }

    /**
     * 导出回传信息列表
     */
    @RequiresPermissions("system:data:export")
    @Log(title = "回传信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDeviceData sysDeviceData) throws Exception {
        List<SysDeviceData> list = sysDeviceDataService.selectSysDeviceDataList(sysDeviceData);
        SXSSFWorkbook wb = new SXSSFWorkbook();
        try{
            Sheet sheet = wb.createSheet("数据");
            sheet.setColumnWidth(0, 8000);
            sheet.setColumnWidth(1, 8000);
            sheet.setColumnWidth(2, 8000);
            sheet.setColumnWidth(3, 8000);
            sheet.setColumnWidth(4, 3000);
            sheet.setColumnWidth(5, 3000);
            sheet.setColumnWidth(6, 3000);
            sheet.setColumnWidth(7, 3000);
            sheet.setColumnWidth(8, 3000);
            sheet.setColumnWidth(9, 3000);
            sheet.setColumnWidth(10, 3000);
            sheet.setColumnWidth(11, 3000);
            sheet.setColumnWidth(12, 3000);
            sheet.setColumnWidth(13, 8000);
            Row row2 = sheet.createRow(0);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("网关编码");
            row2.createCell(1).setCellValue("网关名字");
            row2.createCell(2).setCellValue("设备");
            row2.createCell(3).setCellValue("设备名");
            row2.createCell(4).setCellValue("A流");
            row2.createCell(5).setCellValue("B流");
            row2.createCell(6).setCellValue("C流");
            row2.createCell(7).setCellValue("A压");
            row2.createCell(8).setCellValue("B压");
            row2.createCell(9).setCellValue("C压");
            row2.createCell(10).setCellValue("有功");
            row2.createCell(11).setCellValue("无功");
            row2.createCell(12).setCellValue("因数");
            row2.createCell(13).setCellValue("回传时间");
            Integer index = 1;
            for (SysDeviceData data : list) {
                DeviceDataDto deviceDataDto = new DeviceDataDto();
                DataVo dataVo = JSON.parseObject(data.getResult(), DataVo.class);
                BeanUtils.copyBeanProp(deviceDataDto,data);
                BeanUtils.copyBeanProp(deviceDataDto,dataVo);

                Row row = sheet.createRow(index);
                row.createCell(0).setCellValue(String.valueOf(deviceDataDto.getUuid()));
                if (deviceDataDto.getGroupName() != null) {
                    row.createCell(1).setCellValue(deviceDataDto.getGroupName());
                } else {
                    row.createCell(1).setCellValue(" ");
                }
                row.createCell(2).setCellValue(String.valueOf(deviceDataDto.getDeviceId()));
                if (deviceDataDto.getDeviceName() != null) {
                    row.createCell(3).setCellValue(deviceDataDto.getDeviceName());
                } else {
                    row.createCell(3).setCellValue(" ");
                }
                row.createCell(4).setCellValue(String.valueOf(deviceDataDto.getAi()));
                row.createCell(5).setCellValue(String.valueOf(deviceDataDto.getBi()));
                row.createCell(6).setCellValue(String.valueOf(deviceDataDto.getCi()));
                row.createCell(7).setCellValue(String.valueOf(deviceDataDto.getAv()));
                row.createCell(8).setCellValue(String.valueOf(deviceDataDto.getBv()));
                row.createCell(9).setCellValue(String.valueOf(deviceDataDto.getCv()));
                row.createCell(10).setCellValue(String.valueOf(deviceDataDto.getAw()));
                row.createCell(11).setCellValue(String.valueOf(deviceDataDto.getBw()));
                row.createCell(12).setCellValue(String.valueOf(deviceDataDto.getCw()));
                row.createCell(13).setCellValue(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,deviceDataDto.getCreatTime()));
                index++;
            }
        }catch (Exception e){
            throw new Exception("导出失败");
        }
        FileOutputStream fos = null;
        String absoluteFile = "";
        String name = "回传";
        try {
            absoluteFile = ExcelUtil.getAbsoluteFile(name + "-" + "回传信息"+ "-"+ DateUtils.getDate()+".xlsx");
            fos = new FileOutputStream(absoluteFile);
            wb.write(fos);
            return AjaxResult.success(name + "-" + "回传信息"+ "-"+ DateUtils.getDate()+".xlsx");
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
