package com.ruoyi.web.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class NettyServer implements Runnable {
    private static int bind_port = 8088;
    public static Map<Object, Channel> channelMap = Collections.synchronizedMap(new HashMap<>());
    public static AttributeKey<Object> CODE = AttributeKey.valueOf("code");

    public void run() {

        NioEventLoopGroup nioEventLoopGroup1 = new NioEventLoopGroup();

        NioEventLoopGroup nioEventLoopGroup2 = new NioEventLoopGroup();

        try {
            ServerBootstrap sbs = new ServerBootstrap();

            sbs.group((EventLoopGroup) nioEventLoopGroup1, (EventLoopGroup) nioEventLoopGroup2);

            sbs.channel(NioServerSocketChannel.class);

            sbs.childHandler((ChannelHandler) new ChannelInitializer<SocketChannel>()
                    /*     */ {
                /*     */
                public void initChannel(SocketChannel ch) throws Exception {

                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LineBasedFrameDecoder(1024));
                    pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                    pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

                    pipeline.addLast(new ChannelHandler[]{(ChannelHandler) new IdleStateHandler(0, 0, 0)});

                    pipeline.addLast(new ChannelHandler[]{(ChannelHandler) new NettyServerIdleHandler()});

                    pipeline.addLast(new ChannelHandler[]{(ChannelHandler) new NettyServerHandler()});

                }
                /*     */
            });

            sbs.option(ChannelOption.SO_BACKLOG, Integer.valueOf(1024));

            sbs.childOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));

            sbs.childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));

            log.info("-----------NettyServer Start-----------");


            ChannelFuture future = sbs.bind(bind_port).sync();

            future.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            nioEventLoopGroup2.shutdownGracefully();
            nioEventLoopGroup1.shutdownGracefully();
            log.info("-----------NettyServer Stop-----------");
        }

    }


    public static void registerChannel(Object key, Channel channel) {

        synchronized (channelMap) {

            if (channelMap.containsKey(key)) {

                Channel oldChannel = channelMap.get(key);

                if (!oldChannel.id().equals(channel.id())) {

                    oldChannel.attr(CODE).set(null);

                    oldChannel.closeFuture();

                }

            }

            channelMap.put(key, channel);

            channel.attr(CODE).set(key);

        }

    }


    public static void closeChannel(Channel channel) {

        synchronized (channelMap) {

            Object key = channel.attr(CODE).get();

            if (channelMap.containsKey(key)) {

                ((Channel) channelMap.get(key)).closeFuture();

                channelMap.remove(key);

            }
            log.info("CloseChannel:" + channel.remoteAddress());
            if (key != null && key.toString().startsWith("AC")) {
            }
        }
    }
}
