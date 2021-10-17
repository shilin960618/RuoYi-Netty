package com.ruoyi.web.controller.start;

import com.ruoyi.web.netty.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NettyStart implements CommandLineRunner {
    @Autowired
    private NettyServer server;

    @Override
    public void run(String... args) throws Exception {
        server.run();
    }
}
