package com.ruoyi.web.controller.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDevice;
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
import com.ruoyi.system.domain.SysWorkshop;
import com.ruoyi.system.service.ISysWorkshopService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车间Controller
 * 
 * @author ruoyi
 * @date 2021-11-07
 */
@Controller
@RequestMapping("/system/workshop")
public class SysWorkshopController extends BaseController
{
    private String prefix = "system/workshop";

    @Autowired
    private ISysWorkshopService sysWorkshopService;

    @RequiresPermissions("system:workshop:view")
    @GetMapping()
    public String workshop()
    {
        return prefix + "/workshop";
    }

    /**
     * 查询车间列表
     */
    @RequiresPermissions("system:workshop:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysWorkshop sysWorkshop)
    {
        startPage();
        List<SysWorkshop> list = sysWorkshopService.selectSysWorkshopList(sysWorkshop);
        return getDataTable(list);
    }
    /**
     * 导出车间列表
     */
    @RequiresPermissions("system:workshop:export")
    @Log(title = "车间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysWorkshop sysWorkshop) throws Exception {
        List<SysWorkshop> list = sysWorkshopService.selectSysWorkshopList(sysWorkshop);
        SXSSFWorkbook wb = new SXSSFWorkbook();
        try{
            Sheet sheet = wb.createSheet("车间数据");
            sheet.setColumnWidth(0, 8000);
            sheet.setColumnWidth(1, 8000);
            sheet.setColumnWidth(2, 10000);
            sheet.setColumnWidth(3, 8000);
            Row row2 = sheet.createRow(0);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("车间名称");
            row2.createCell(1).setCellValue("排序");
            row2.createCell(2).setCellValue("备注");
            row2.createCell(3).setCellValue("创建时间");
            Integer index = 1;
            for (SysWorkshop sysWorkshopResult : list) {
                Row row = sheet.createRow(index);
                row.createCell(0).setCellValue(sysWorkshopResult.getWorkshop());
                row.createCell(1).setCellValue(sysWorkshopResult.getSort());
                row.createCell(2).setCellValue(sysWorkshopResult.getRemark());
                row.createCell(3).setCellValue(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,sysWorkshopResult.getCreateTime()));
                index++;
            }
        }catch (Exception e){
            throw new Exception("导出失败");
        }
        FileOutputStream fos = null;
        String absoluteFile = "";
        String name = "车间";
        try {
            absoluteFile = ExcelUtil.getAbsoluteFile(name + "-" + "车间数据"+ "-"+ DateUtils.getDate()+".xlsx");
            fos = new FileOutputStream(absoluteFile);
            wb.write(fos);
            return AjaxResult.success(name + "-" + "车间数据"+ "-"+ DateUtils.getDate()+".xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            wb.close();
            fos.close();
        }
        return null;
    }

    /**
     * 新增车间
     */
    @GetMapping("/add/{companyId}")
    public String add(@PathVariable("companyId") String companyId, ModelMap mmap)
    {
        mmap.put("companyId", companyId);
        return prefix + "/add";
    }

    /**
     * 新增保存车间
     */
    @RequiresPermissions("system:workshop:add")
    @Log(title = "车间", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysWorkshop sysWorkshop)
    {
        return toAjax(sysWorkshopService.insertSysWorkshop(sysWorkshop));
    }

    /**
     * 修改车间
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysWorkshop sysWorkshop = sysWorkshopService.selectSysWorkshopById(id);
        mmap.put("sysWorkshop", sysWorkshop);
        return prefix + "/edit";
    }

    /**
     * 修改保存车间
     */
    @RequiresPermissions("system:workshop:edit")
    @Log(title = "车间", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysWorkshop sysWorkshop)
    {
        return toAjax(sysWorkshopService.updateSysWorkshop(sysWorkshop));
    }

    /**
     * 删除车间
     */
    @RequiresPermissions("system:workshop:remove")
    @Log(title = "车间", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysWorkshopService.deleteSysWorkshopByIds(ids));
    }
}
