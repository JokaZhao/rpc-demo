package com.joka.rpc.async;

import com.joka.rpc.client.RPCFuture;
import com.joka.rpc.protocol.AsyncRPCCallback;
import com.joka.rpc.protocol.IAsyncObjectProxy;
import com.joka.rpc.proxy.RpcProxy;
import com.joka.rpc.test.HelloService;

/**
 * Created on 2019/8/2 12:16.
 *
 * @author zhaozengjie
 * Description :
 */
public class AsycComsumer {

    public static void main(String[] args) {
        IAsyncObjectProxy async = RpcProxy.createAsync(HelloService.class);
        RPCFuture call = async.call("hello", "zhaozengjie");
        call.addCallback(new AsyncRPCCallback() {
            @Override
            public void success(Object result) {
                System.out.println("async response :" + result);
            }

            @Override
            public void fail(Exception e) {
                System.out.println("async error :" + e.getMessage());
            }
        });

    }
}
