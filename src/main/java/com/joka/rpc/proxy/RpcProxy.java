package com.joka.rpc.proxy;

import com.joka.rpc.protocol.IAsyncObjectProxy;

import java.lang.reflect.Proxy;

/**
 * Created on 2019/8/2 11:12.
 *
 * @author zhaozengjie
 * Description :
 */
public class RpcProxy {

    public static <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvocation<>(interfaceClass)
        );
    }

    public static <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new RpcInvocation<T>(interfaceClass);
    }

}
