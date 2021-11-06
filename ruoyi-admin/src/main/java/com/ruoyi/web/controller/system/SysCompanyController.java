package com.ruoyi.web.controller.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.Dto.SysCompanyDto;
import com.ruoyi.system.domain.SysGateway;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysCompany;
import com.ruoyi.system.service.ISysCompanyService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业Controller
 * 
 * @author ruoyi
 * @date 2021-11-03
 */
@Controller
@RequestMapping("/system/company")
public class SysCompanyController extends BaseController
{
    private String prefix = "system/company";

    private String sysDictIndustry = "sys_dict_industry";

    private String sysDictStreet = "sys_dict_street";

    @Autowired
    private ISysCompanyService sysCompanyService;

    @RequiresPermissions("system:company:view")
    @GetMapping()
    public String company()
    {
        return prefix + "/company";
    }

    /**
     * 查询企业列表
     */
    @RequiresPermissions("system:company:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCompany sysCompany)
    {
        startPage();
        List<SysCompany> list = sysCompanyService.selectSysCompanyList(sysCompany);
        TableDataInfo dataTable = getDataTable(list);

        List<SysCompanyDto> sysCompanyDtoList = new ArrayList<>();
        for (SysCompany sysCompanyResult:list) {
            SysCompanyDto sysCompanyDto = new SysCompanyDto();
            BeanUtils.copyBeanProp(sysCompanyDto,sysCompanyResult);
            if (!ObjectUtils.isEmpty(sysCompanyResult.getIndustry())) {
                Long industry = sysCompanyResult.getIndustry();
                String industryName = DictUtils.getDictLabel(sysDictIndustry, industry.toString());
                sysCompanyDto.setIndustryName(industryName);
            }
            if (!ObjectUtils.isEmpty(sysCompanyResult.getStreetId())) {
                Long streetId = sysCompanyResult.getStreetId();
                String streetName = DictUtils.getDictLabel(sysDictStreet, streetId.toString());
                sysCompanyDto.setStreetName(streetName);
            }
            sysCompanyDtoList.add(sysCompanyDto);
        }
        dataTable.setRows(sysCompanyDtoList);
        return dataTable;
    }

    /**
     * 导出企业列表
     */
    @RequiresPermissions("system:company:export")
    @Log(title = "企业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCompany sysCompany) throws Exception {
        List<SysCompany> list = sysCompanyService.selectSysCompanyList(sysCompany);
        List<SysCompanyDto> sysCompanyDtoList = new ArrayList<>();
        for (SysCompany sysCompanyResult:list) {
            SysCompanyDto sysCompanyDto = new SysCompanyDto();
            BeanUtils.copyBeanProp(sysCompanyDto,sysCompanyResult);
            if (!ObjectUtils.isEmpty(sysCompanyResult.getIndustry())) {
                Long industry = sysCompanyResult.getIndustry();
                String industryName = DictUtils.getDictLabel(sysDictIndustry, industry.toString());
                sysCompanyDto.setIndustryName(industryName);
            }
            if (!ObjectUtils.isEmpty(sysCompanyResult.getStreetId())) {
                Long streetId = sysCompanyResult.getStreetId();
                String streetName = DictUtils.getDictLabel(sysDictStreet, streetId.toString());
                sysCompanyDto.setStreetName(streetName);
            }
            if (StringUtils.isNotEmpty(sysCompanyResult.getIsSms()) && sysCompanyResult.getIsSms().equals("0")) {
                sysCompanyDto.setSmsName("打开");
            } else {
                sysCompanyDto.setSmsName("关闭");
            }
            sysCompanyDtoList.add(sysCompanyDto);
        }

        SXSSFWorkbook wb = new SXSSFWorkbook();
        try{
            Sheet sheet = wb.createSheet("企业数据");
            sheet.setColumnWidth(0, 10000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 10000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 5000);
            sheet.setColumnWidth(11, 5000);
            sheet.setColumnWidth(12, 10000);
            sheet.setColumnWidth(13, 5000);
            sheet.setColumnWidth(14, 5000);
            sheet.setColumnWidth(15, 5000);
            Row row2 = sheet.createRow(0);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("企业名称");
            row2.createCell(1).setCellValue("统一社会信用代码");
            row2.createCell(2).setCellValue("街道");
            row2.createCell(3).setCellValue("详细地址");
            row2.createCell(4).setCellValue("行业分类");
            row2.createCell(5).setCellValue("法人");
            row2.createCell(6).setCellValue("联系手机");
            row2.createCell(7).setCellValue("电话");
            row2.createCell(8).setCellValue("短信状态");
            row2.createCell(9).setCellValue("环保负责人");
            row2.createCell(10).setCellValue("经度");
            row2.createCell(11).setCellValue("纬度");
            row2.createCell(12).setCellValue("备注");
            row2.createCell(13).setCellValue("MN码");
            row2.createCell(14).setCellValue("网关设备号");
            row2.createCell(15).setCellValue("网关频道号");
            Integer index = 1;
            for (SysCompanyDto sysCompanyDto : sysCompanyDtoList) {
                Row row = sheet.createRow(index);
                row.createCell(0).setCellValue(sysCompanyDto.getCompanyName());
                row.createCell(1).setCellValue(sysCompanyDto.getCompanyCode());
                row.createCell(2).setCellValue(sysCompanyDto.getStreetName());
                row.createCell(3).setCellValue(sysCompanyDto.getAddress());
                row.createCell(4).setCellValue(sysCompanyDto.getIndustryName());
                row.createCell(5).setCellValue(sysCompanyDto.getLegalPerson());
                row.createCell(6).setCellValue(sysCompanyDto.getMobilePhone());
                row.createCell(7).setCellValue(sysCompanyDto.getPhone());
                row.createCell(8).setCellValue(sysCompanyDto.getSmsName());
                row.createCell(9).setCellValue(sysCompanyDto.getPrincipal());
                row.createCell(10).setCellValue(sysCompanyDto.getLongitude());
                row.createCell(11).setCellValue(sysCompanyDto.getLatitude());
                row.createCell(12).setCellValue(sysCompanyDto.getRemark());
                row.createCell(13).setCellValue(sysCompanyDto.getMnCode());
                row.createCell(14).setCellValue(sysCompanyDto.getWayCode());
                row.createCell(15).setCellValue(sysCompanyDto.getWayCodeChannel());
                index++;
            }
        }catch (Exception e){
            throw new Exception("导出失败");
        }
        FileOutputStream fos = null;
        String absoluteFile = "";
        String name = "企业";
        try {
            absoluteFile = ExcelUtil.getAbsoluteFile(name + "-" + "企业数据"+ "-"+ DateUtils.getDate()+".xlsx");
            fos = new FileOutputStream(absoluteFile);
            wb.write(fos);
            return AjaxResult.success(name + "-" + "企业数据"+ "-"+ DateUtils.getDate()+".xlsx");
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
     * 新增企业
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存企业
     */
    @RequiresPermissions("system:company:add")
    @Log(title = "企业", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCompany sysCompany)
    {
        return toAjax(sysCompanyService.insertSysCompany(sysCompany));
    }

    /**
     * 修改企业
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysCompany sysCompany = sysCompanyService.selectSysCompanyById(id);
        mmap.put("sysCompany", sysCompany);
        return prefix + "/edit";
    }

    /**
     * 修改保存企业
     */
    @RequiresPermissions("system:company:edit")
    @Log(title = "企业", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCompany sysCompany)
    {
        return toAjax(sysCompanyService.updateSysCompany(sysCompany));
    }

    /**
     * 删除企业
     */
    @RequiresPermissions("system:company:remove")
    @Log(title = "企业", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysCompanyService.deleteSysCompanyByIds(ids));
    }
}
