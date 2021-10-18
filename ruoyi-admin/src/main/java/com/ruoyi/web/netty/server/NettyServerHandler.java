package com.ruoyi.web.netty.server;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
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
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
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
        String obj1 = (String) obj;
        log.info(obj1 + "  接收参数时间 " + DateUtils.getTime());
        T212Mapper mapper = new T212Mapper()
                .enableDefaultVerifyFeatures()
                .enableDefaultParserFeatures();
        //从T212字符串中读取Data对象
        Data data = mapper.readData(obj1 + "\r\n");
        log.info(data.toString() + "  ----------接收对象时间 " + DateUtils.getTime());
        byte[] b = obj1.getBytes();
        try {
            String str = new String(b, "utf-8");
            System.out.println(str + "123456");
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
