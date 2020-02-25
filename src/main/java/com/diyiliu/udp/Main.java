package com.diyiliu.udp;

import com.diyiliu.udp.netty.UdpServer;

/**
 * Description: Main
 * Author: DIYILIU
 * Update: 2020-02-22 14:15
 */
public class Main {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5006;

        if (args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        if (args.length > 1){
            host = args[1];
        }

        // 多网卡时需要绑定IP
        new UdpServer().bind(host, port);
    }
}
