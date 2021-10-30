package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
@EnableScheduling
public class DeviceTask {

    @Autowired
    private ISysDeviceService sysDeviceService;

    @Autowired
    private ISysConfigService configService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void deviceDown(){
        SysDevice sysDevice = new SysDevice();
        List<SysDevice> list = sysDeviceService.selectSysDeviceList(sysDevice);
        String key = configService.selectConfigByKey("sys.sensor.offline");
        if (StringUtils.isEmpty(key)) {
            key = "30";
        }
        Long offline = Long.valueOf(key);
        for(SysDevice sysDevice1:list){
            if(StringUtils.isNotEmpty(sysDevice1.getSendTime())){
                long dateTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, sysDevice1.getSendTime()).getTime();
                long nowTime = DateUtils.getNowDate().getTime();
                long min = (nowTime - dateTime) / (60 * 1000);
                if(min > offline){
                    SysDevice sysDeviceUpdate = new SysDevice();
                    sysDeviceUpdate.setIsOnline("2");
                    sysDeviceUpdate.setId(sysDevice1.getId());
                    sysDeviceService.updateSysDevice(sysDeviceUpdate);
                }
            }
        }
    }
}
