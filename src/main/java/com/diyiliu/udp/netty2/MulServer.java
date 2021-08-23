package com.diyiliu.udp.netty2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.NetUtil;
import io.netty.util.internal.SocketUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * MulServer
 * <p>
 * 组播
 * 224.0.0.0～224.0.0.255为预留的组播地址（永久组地址），地址224.0.0.0保留不做分配，其它地址供路由协议使用；
 * 224.0.1.0～224.0.1.255是公用组播地址，Internetwork Control Block；
 * 224.0.2.0～238.255.255.255为用户可用的组播地址（临时组地址），全网范围内有效；
 * 239.0.0.0～239.255.255.255为本地管理组播地址，仅在特定的本地范围内有效。
 *
 * @author: DIYILIU
 * @date: 2021/08/23
 */
public class MulServer {

    public static void main(String[] args) {
        String host = "238.0.0.1";
        int port = 9000;

        InetSocketAddress groupAddress = new InetSocketAddress(host, port);
        InetAddress localAddress = getLocalAddress();

        NetworkInterface ni = NetUtil.LOOPBACK_IF;
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bs = new Bootstrap();
        bs.group(group)
                .channelFactory((ChannelFactory<NioDatagramChannel>) () -> new NioDatagramChannel(InternetProtocolFamily.IPv4))
                .option(ChannelOption.IP_MULTICAST_IF, ni)
                .option(ChannelOption.IP_MULTICAST_ADDR, localAddress)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) {
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                                ByteBuf buf = msg.content();
                                byte[] bytes = new byte[buf.readableBytes()];
                                buf.readBytes(bytes);

                                System.out.println("server: " + new String(bytes));

                                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes), SocketUtils.socketAddress(localAddress.getHostAddress(), 9001)));
                            }
                        });
                    }
                });

        try {
            NioDatagramChannel channel = (NioDatagramChannel) bs.bind(localAddress, port).sync().channel();
            channel.joinGroup(groupAddress, ni).sync();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static InetAddress getLocalAddress() {
        NetworkInterface ni = NetUtil.LOOPBACK_IF;
        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

        InetAddress localAddress = null;
        while (inetAddresses.hasMoreElements()) {
            InetAddress address = inetAddresses.nextElement();
            if (address instanceof Inet4Address) {
                localAddress = address;
            }
        }

        return localAddress;
    }
}
