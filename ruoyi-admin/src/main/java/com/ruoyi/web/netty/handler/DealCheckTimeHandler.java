package com.ruoyi.web.netty.handler;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.netty.hj212.parser.core.T212Mapper;
import com.ruoyi.web.netty.hj212.parser.exception.T212FormatException;
import com.ruoyi.web.netty.hj212.parser.model.CpData;
import com.ruoyi.web.netty.hj212.parser.model.Data;
import com.ruoyi.web.netty.hj212.parser.model.DataFlag;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DealCheckTimeHandler {

    // 请求校时
    public void dealCheckTime(Channel incomming, Data data){
        // 首先返回请求应答
        List<DataFlag> dataFlagList = new ArrayList<>(1);
        dataFlagList.add(DataFlag.V0);
        // 构建返回数据
        Data dataResult = new Data();
        dataResult.setQn(data.getQn());
        dataResult.setSt("91");
        dataResult.setCn("9013");
        dataResult.setPw(data.getPw());
        dataResult.setMn(data.getMn());
        CpData cpResult = new CpData();
        dataResult.setDataFlag(dataFlagList);
        dataResult.setCp(cpResult);
        T212Mapper mapper = new T212Mapper().enableDefaultParserFeatures().enableDefaultVerifyFeatures();
        String result;
        try {
            result = mapper.writeDataAsString(dataResult);
            incomming.writeAndFlush(result);
            log.info("回传数据为" + result);
        }catch (IOException | T212FormatException e){
            log.error("回传数据异常" + e.getLocalizedMessage());
        }



        List<DataFlag> dataFlagDateList = new ArrayList<>(1);
        dataFlagDateList.add(DataFlag.V0);
        dataFlagDateList.add(DataFlag.A);
        // 返回设置当前时间
        Data dataResultDate = new Data();
        dataResultDate.setQn(data.getQn());
        dataResultDate.setSt("80");
        dataResultDate.setCn("1012");
        dataResultDate.setPw(data.getPw());
        dataResultDate.setMn(data.getMn());
        dataResultDate.setDataFlag(dataFlagDateList);
        CpData cpDate = new CpData();
        cpDate.setSystemTime(DateUtils.dateTimeNow());
        dataResultDate.setCp(cpDate);
        try {
            result = mapper.writeDataAsString(dataResultDate);
            incomming.writeAndFlush(result);
            log.info("回传数据为" + result);
        }catch (IOException | T212FormatException e){
            log.error("回传数据异常" + e.getLocalizedMessage());
        }
    }
}
