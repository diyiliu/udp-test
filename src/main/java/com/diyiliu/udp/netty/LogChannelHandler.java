package com.diyiliu.udp.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Description: LogChannelHandler
 * Author: DIYILIU
 * Update: 2020-02-22 14:17
 */

@Slf4j
public class LogChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DatagramPacket packet = (DatagramPacket) msg;
        InetSocketAddress address = packet.sender();

        String remote = address.getHostString() + ":" + address.getPort();

        ByteBuf buf = packet.content();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);


        String str = new String(bytes, "GBK");
        log.info("接收[{}]消息: [string={}, bytes={}]", remote, str, bytesToHex(str.getBytes()));

        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender()));
    }

    public String bytesToHex(byte[] bytes){
        StringBuffer str = new StringBuffer();
        for (byte b: bytes){
            str.append(String.format("%02X", b & 0xFF));
        }

        return str.toString();
    }
}
