package com.diyiliu.udp;

import com.diyiliu.udp.netty.UdpServer;

/**
 * Description: Main
 * Author: DIYILIU
 * Update: 2020-02-22 14:15
 */
public class Main {

    public static void main(String[] args) {

        // 多网卡时需要绑定IP
        new UdpServer().bind("192.168.2.140", 5006);
    }
}
