package com.ruoyi.web.netty.server;


import com.ruoyi.common.utils.DateUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyServerIdleHandler
        extends ChannelInboundHandlerAdapter {
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof io.netty.handler.timeout.IdleStateEvent) {
            log.info("----ChannelIdle----" + DateUtils.dateTime());
            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

