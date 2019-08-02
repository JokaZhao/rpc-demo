package com.joka.rpc.demo;

import com.joka.rpc.test.HelloService;
import com.joka.rpc.proxy.RpcProxy;

/**
 * Created on 2019/8/2 11:46.
 *
 * @author zhaozengjie
 * Description :
 */
public class Comsumer {

    public static void main(String[] args) {
        HelloService helloService = RpcProxy.create(HelloService.class);
        String s = helloService.hello("zhaozengjie");
        System.out.println("response :" + s);
    }

}
