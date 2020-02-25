package com.diyiliu.udp;

import com.diyiliu.udp.netty.UdpServer;

/**
 * Description: Main
 * Author: DIYILIU
 * Update: 2020-02-22 14:15
 */
public class Main {

    public static void main(String[] args) {
        int port = 5006;

        if (args.length > 1) {
            new UdpServer().bind(args[0], Integer.parseInt(args[1]));
        } else {
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }

            new UdpServer().bind(port);
        }
    }
}
