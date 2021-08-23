package com.diyiliu.udp.netty2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.NetUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;

/**
 * MulClient
 *
 * @author: DIYILIU
 * @date: 2021/08/23
 */
public class MulClient {

    public static void main(String[] args) {
        String host = "238.0.0.1";
        int port = 9000;

        InetSocketAddress groupAddress = new InetSocketAddress(host, port);
        InetAddress localAddress = MulServer.getLocalAddress();

        NetworkInterface ni = NetUtil.LOOPBACK_IF;
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bs = new Bootstrap();
        bs.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.IP_MULTICAST_IF, ni)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) {
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket msg) throws Exception {

                                ByteBuf buf = msg.content();
                                byte[] bytes = new byte[buf.readableBytes()];
                                buf.readBytes(bytes);

                                System.out.println("client: " + new String(bytes));
                            }
                        });
                    }
                });

        try {
            Channel ch = bs.bind(localAddress, 9001).sync().channel();
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("hello, World", Charset.defaultCharset()), groupAddress));
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }
}
