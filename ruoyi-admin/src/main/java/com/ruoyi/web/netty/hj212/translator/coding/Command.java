package com.ruoyi.web.netty.hj212.translator.coding;


import com.ruoyi.web.netty.hj212.translator.CodeMean;

/**
 * 请求命令返回（可扩充）
 * Created on 2017/12/30.
 */
public enum Command implements CodeMean {

    //初始化命令
    _1000("设置超时时间及重发次数"),

    //参数命令
    _1011("提取现场机时间/上传现场机时间"),
    _1012("设置现场机时间"),
    _1013("现场机时间校准请求"),
    _1061("提取实时数据间隔/上传实时数据间隔"),
    _1062("设置实时数据间隔"),
    _1063("提取分钟数据间隔/上传分钟数据间隔"),
    _1064("设置分钟数据间隔"),
    _1072("设置现场机密码"),

    //数据命令
        //实时数据
    _2011("取污染物实时数据/上传污染物实时数据"),
    _2012("停止察看污染物实时数据"),
        //设备状态
    _2021("取设备运行状态数据/上传设备运行状态数据"),
    _2022("停止察看设备运行状态"),
        //日数据
    _2031("取污染物日历史数据/上传污染物日历史数据"),
    _2041("取设备运行时间日历史数据/上传设备运行时间日历史数据"),
        //分钟数据
    _2051("取污染物分钟数据/上传污染物分钟数据"),
        //小时数据
    _2061("取污染物小时数据/上传污染物小时数据"),
        //其它数据
    _2081("上传数采仪开机时间"),

    //控制命令
    _3011("零点校准量程校准"),
    _3012("即时采样"),
    _3013("启动清洗/反吹 "),
    _3014("比对采样"),
    _3015("超标留样/上传超标留样信息"),
    _3016("设置采样时间周期"),
    _3017("提取采样时间周期/上传采样时间周期"),
    _3018("提取出样时间/上传出样时间"),
    _3019("提取设备唯一标识/上传设备唯一标识"),
    _3020("提取现场机信息/上传现场机信息"),
    _3021("设置现场机参数"),

    //交互命令
    _9011("请求应答"),
    _9012("执行结果"),
    _9013("通知应答"),
    _9014("数据应答");

    private String code;
    private String meaning;

    Command(String meaning){
        this.code = name().substring(1);
        this.meaning = meaning;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String mean() {
        return meaning;
    }
}
