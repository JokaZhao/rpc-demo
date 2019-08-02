package com.joka.rpc;

import com.joka.rpc.test.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created on 2019/7/31 16:40.
 *
 * @author zhaozengjie
 * Description :
 */
public class RPCProxyClient implements InvocationHandler {

    private Object object;

    public RPCProxyClient(Object object) {
        this.object = object;
    }
    /**
     * 得到被代理对象;
     */
    public static Object getProxy(Object obj){
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), new RPCProxyClient(obj));
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //结果参数;
        Object result = new Object();
        // ...执行通信相关逻辑
        // ...
        return result;
    }

    public static class Test{
        public static void main(String[] args) {
            HelloService proxy = (HelloService) RPCProxyClient.getProxy(HelloService.class);
            proxy.hello("zhaozengjie");
        }
    }
}


