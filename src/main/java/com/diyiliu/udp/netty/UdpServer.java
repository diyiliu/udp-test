package com.diyiliu.udp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: UdpServer
 * Author: DIYILIU
 * Update: 2020-02-22 14:16
 */

@Slf4j
public class UdpServer {

    public void bind(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();

            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new LogChannelHandler());

            log.info("UDP服务器启动[{}]...", port);
            b.bind(host, port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("UDP服务器已停止!");
            group.shutdownGracefully();
        }
    }
}
