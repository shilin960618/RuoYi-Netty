package com.ruoyi.web.netty.handler;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.domain.SysDeviceData;
import com.ruoyi.system.domain.SysGateway;
import com.ruoyi.system.service.ISysDeviceDataService;
import com.ruoyi.system.service.ISysDeviceService;
import com.ruoyi.system.service.ISysGatewayService;
import com.ruoyi.system.vo.DataVo;
import com.ruoyi.web.netty.hj212.parser.model.CpData;
import com.ruoyi.web.netty.hj212.parser.model.Data;
import com.ruoyi.web.netty.hj212.parser.model.Pollution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ResultDataHandler {

    @Autowired
    private ISysGatewayService iSysGatewayService;

    @Autowired
    private ISysDeviceService iSysDeviceService;

    @Autowired
    private ISysDeviceDataService iSysDeviceDataService;

    public void dealResultData(Data data){
        //说明上传实时数据
        String mn = data.getMn();
        // 首先查询这个网关存不存在，不存在则新增
        SysGateway sysGatewaySelect = new SysGateway();
        sysGatewaySelect.setUuid(mn);
        List<SysGateway> gateways = iSysGatewayService.selectSysGatewayList(sysGatewaySelect);
        String groupName = " ";
        if (gateways.isEmpty()) {
            // 说明不存在，要新增网关
            SysGateway sysGatewayInsert = new SysGateway();
            sysGatewayInsert.setUuid(mn);
            sysGatewayInsert.setStatus(1L);
            sysGatewayInsert.setCreateTime(DateUtils.getNowDate());
            sysGatewayInsert.setUpdateTime(DateUtils.getNowDate());
            iSysGatewayService.insertSysGateway(sysGatewayInsert);
        } else {
            SysGateway sysGatewayUpdtae = gateways.get(0);
            groupName = sysGatewayUpdtae.getName();
            sysGatewayUpdtae.setStatus(1L);
            sysGatewayUpdtae.setUpdateTime(DateUtils.getNowDate());
            iSysGatewayService.updateSysGateway(sysGatewayUpdtae);
        }

        CpData cp = data.getCp();
        // 获取时间
        String dataTime = cp.getDataTime();
        // 搞数据
        Map<String, Pollution> pollutionMap = cp.getPollution();
        // 校验数据，如果设备id不一致，直接return
        HashSet<String> setKey = new HashSet<>();
        for (String key : pollutionMap.keySet()) {
            if (key.length() == 10 && key.substring(0,5).equals("ea300")) {
                setKey.add(key.substring(0,8));
            }
        }
        if (setKey.size() != 1) {
            return;
        }
        Iterator<Map.Entry<String, Pollution>> iterator = pollutionMap.entrySet().iterator();
        // 构造数据
        DataVo dataVo = new DataVo();
        while (iterator.hasNext()) {
            String key = iterator.next().getKey();
            Pollution pollution = pollutionMap.get(key);
            if (key.length() == 10 && key.substring(0,5).equals("ea300")) {
                String functionId = key.substring(key.length() - 2);
                switch(functionId){
                    case "01" :
                        // A 相电流
                        dataVo.setAi(pollution.getRtd().toString());
                        break;
                    case "02" :
                        // B 相电流
                        dataVo.setBi(pollution.getRtd().toString());
                        break;
                    case "03" :
                        // C 相电流
                        dataVo.setCi(pollution.getRtd().toString());
                        break;
                    case "10" :
                        // A 相电压
                        dataVo.setAv(pollution.getRtd().toString());
                        break;
                    case "11" :
                        // B 相电压
                        dataVo.setBv(pollution.getRtd().toString());
                        break;
                    case "12" :
                        // C 相电压
                        dataVo.setCv(pollution.getRtd().toString());
                        break;
                    case "05" :
                        // 总有功功率
                        dataVo.setAw(pollution.getRtd().toString());
                        break;
                    case "06" :
                        // 总无功功率
                        dataVo.setBw(pollution.getRtd().toString());
                        break;
                    case "09" :
                        // 功率因数
                        dataVo.setCw(pollution.getRtd().toString());
                        break;
                    default : //可选
                        //语句
                }
            }
        }

        String nodeId = (String)setKey.toArray()[0];
        String substringNodeId = nodeId.substring(nodeId.length() - 2, nodeId.length());
        // 首先查询网关下面的节点是否存在
        SysDevice sysDeviceSelect = new SysDevice();
        sysDeviceSelect.setUuid(mn);
        sysDeviceSelect.setNodeId(Long.valueOf(substringNodeId));
        List<SysDevice> sysDeviceList = iSysDeviceService.selectSysDeviceList(sysDeviceSelect);
        String deviceName = " ";
        if (sysDeviceList.isEmpty()) {
            // 说明不存在，要新增
            SysDevice sysDeviceInsert = new SysDevice();
            sysDeviceInsert.setNodeId(Long.valueOf(substringNodeId));
            sysDeviceInsert.setUuid(mn);
            sysDeviceInsert.setIsOnline("1");
            sysDeviceInsert.setSendTime(dataTime);
            sysDeviceInsert.setData(JSON.toJSONString(dataVo));
            sysDeviceInsert.setCreateTime(DateUtils.getNowDate());
            sysDeviceInsert.setUpdateTime(DateUtils.getNowDate());
            iSysDeviceService.insertSysDevice(sysDeviceInsert);
        } else {
            // 进行更新
            SysDevice sysDeviceUpdate = sysDeviceList.get(0);
            deviceName = sysDeviceUpdate.getDeviceName();
            sysDeviceUpdate.setIsOnline("1");
            sysDeviceUpdate.setSendTime(dataTime);
            sysDeviceUpdate.setData(JSON.toJSONString(dataVo));
            sysDeviceUpdate.setUpdateTime(DateUtils.getNowDate());
            iSysDeviceService.updateSysDevice(sysDeviceUpdate);
        }

        // 将数据插入数据表
        SysDeviceData sysDeviceDataInsert = new SysDeviceData();
        sysDeviceDataInsert.setUuid(mn);
        sysDeviceDataInsert.setGroupName(groupName);
        sysDeviceDataInsert.setDeviceId(Long.valueOf(substringNodeId));
        sysDeviceDataInsert.setDeviceName(deviceName);
        sysDeviceDataInsert.setCreatTime(DateUtils.getNowDate());
        sysDeviceDataInsert.setResult(JSON.toJSONString(dataVo));
        iSysDeviceDataService.insertSysDeviceData(sysDeviceDataInsert);
    }
}
