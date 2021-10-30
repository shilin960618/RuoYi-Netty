package com.ruoyi.web.netty.server;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.web.netty.handler.ResultDataHandler;
import com.ruoyi.web.netty.hj212.parser.core.T212Mapper;
import com.ruoyi.web.netty.hj212.parser.model.Data;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;


@Slf4j
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ResultDataHandler resultDataHandler = SpringUtils.getBean("resultDataHandler");

    public static ChannelGroup channels = (ChannelGroup) new DefaultChannelGroup(
            (EventExecutor) GlobalEventExecutor.INSTANCE);

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incomming = ctx.channel();
        log.info("ChannelAdded:" + incomming.remoteAddress());
        channels.add(ctx.channel());
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incomming = ctx.channel();
        log.info("ChannelRemoved:" + incomming.remoteAddress());

        channels.remove(ctx.channel());
    }

    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        Channel incomming = ctx.channel();
        String msg = new String(((String) obj).getBytes(), "utf-8");
        if (msg.length() > 2) {
            String substring = msg.substring(0, 2);
            if (!substring.equals("##")) {
                return;
            }
        }
        log.info(msg + "  接收参数时间 " + DateUtils.getTime());
        T212Mapper mapper = new T212Mapper()
                .enableDefaultVerifyFeatures()
                .enableDefaultParserFeatures();
        //从T212字符串中读取Data对象
        Data data = mapper.readData(msg + "\r\n");
        try {
            switch(data.getCn()){
                case "2011" :
                    //说明上传实时数据
                    resultDataHandler.dealResultData(incomming,data);
                    break; //可选
                case "9011" :
                    //说明请求应答
                    break; //可选
                //你可以有任意数量的case语句
                default : //可选
                    //语句
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("ChannelActive:" + incoming.remoteAddress());
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("ChannelInactive:" + incoming.remoteAddress());
        NettyServer.closeChannel(incoming);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.info("ExceptionCaught:" + incoming.remoteAddress());

        ctx.close();
        log.error(cause.getMessage());
    }

}
