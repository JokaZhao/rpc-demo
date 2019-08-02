package com.joka.rpc.proxy;

import com.joka.rpc.client.ConnectMange;
import com.joka.rpc.client.RPCFuture;
import com.joka.rpc.client.RpcContext;
import com.joka.rpc.protocol.IAsyncObjectProxy;
import com.joka.rpc.protocol.RequestPacket;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import io.netty.channel.*;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created on 2019/8/2 10:33.
 *
 * @author zhaozengjie
 * Description :
 */
public class RpcInvocation<T> implements InvocationHandler, IAsyncObjectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcInvocation.class);
    private Class<T> clazz;

    public RpcInvocation(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestPacket request = createRequest(this.clazz.getName(), method.getName(), args);
        Channel channel = ConnectMange.getInstance().choose();
        RPCFuture rpcFuture = sendRequest(request, channel);
        return rpcFuture.get();
    }

    @Override
    public RPCFuture call(String funcName, Object... args) {
        RequestPacket request = createRequest(this.clazz.getName(), funcName, args);
        Channel channel = ConnectMange.getInstance().choose();
        RPCFuture rpcFuture = sendRequest(request, channel);
        return rpcFuture;
    }

    private RequestPacket createRequest(String className, String methodName, Object[] args) {
        RequestPacket request = new RequestPacket();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameters(args);

        if (args != null && args.length > 0) {
            Class[] parameterTypes = new Class[args.length];
            // Get the right class type
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = getClassType(args[i]);
            }
            request.setParameterTypes(parameterTypes);
            for (int i = 0; i < parameterTypes.length; ++i) {
                LOGGER.debug(parameterTypes[i].getName());
            }
            for (int i = 0; i < args.length; ++i) {
                LOGGER.debug(args[i].toString());
            }
        }

        // Debug
        LOGGER.debug(className);
        LOGGER.debug(methodName);

        return request;
    }

    private RPCFuture sendRequest(RequestPacket request, Channel channel) {
        final CountDownLatch latch = new CountDownLatch(1);
        RPCFuture rpcFuture = new RPCFuture(request);
        RpcContext.add(request.getRequestId(), rpcFuture);
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return rpcFuture;
    }

    private Class<?> getClassType(Object obj) {
        Class<?> classType = obj.getClass();
        String typeName = classType.getName();
        switch (typeName) {
            case "java.lang.Integer":
                return Integer.TYPE;
            case "java.lang.Long":
                return Long.TYPE;
            case "java.lang.Float":
                return Float.TYPE;
            case "java.lang.Double":
                return Double.TYPE;
            case "java.lang.Character":
                return Character.TYPE;
            case "java.lang.Boolean":
                return Boolean.TYPE;
            case "java.lang.Short":
                return Short.TYPE;
            case "java.lang.Byte":
                return Byte.TYPE;
        }

        return classType;
    }

}
