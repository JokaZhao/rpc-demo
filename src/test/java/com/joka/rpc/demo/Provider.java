package com.joka.rpc.demo;

import com.joka.rpc.server.NettyServer;
import com.joka.rpc.test.HelloService;
import com.joka.rpc.test.HelloServiceImpl;

/**
 * Created on 2019/8/2 11:31.
 *
 * @author zhaozengjie
 * Description :
 */
public class Provider {

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        HelloService helloService = new HelloServiceImpl();
        server.addService("com.joka.rpc.test.HelloService", helloService);
        try {
            server.start();
        } catch (Exception ex) {
            System.out.println("Exception: {}"+ ex);
        }
    }

}
