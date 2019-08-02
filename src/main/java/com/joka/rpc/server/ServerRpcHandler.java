package com.joka.rpc.server;

import com.joka.rpc.protocol.RequestPacket;
import com.joka.rpc.protocol.ResponsePacket;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created on 2019/8/2 9:38.
 *
 * @author zhaozengjie
 * Description :
 */
public class ServerRpcHandler extends SimpleChannelInboundHandler<RequestPacket> {

    private static final Logger logger = LoggerFactory.getLogger(ServerRpcHandler.class);

    private final Map<String, Object> handlerMap;

    public ServerRpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestPacket request) throws Exception {

        Pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Receive request " + request.getRequestId());
                ResponsePacket response = new ResponsePacket();
                response.setRequestId(request.getRequestId());
                try {
                    Object result = handle(request);
                    response.setResult(result);
                } catch (Throwable t) {
                    response.setError(t.toString());
                    System.out.println("RPC Server handle request error:" + t);
                }
                channelHandlerContext.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("Send response for request " + request.getRequestId());
                    }
                });
            }
        });

    }

    private Object handle(RequestPacket request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        logger.debug(serviceClass.getName());
        logger.debug(methodName);
        for (int i = 0; i < parameterTypes.length; ++i) {
            logger.debug(parameterTypes[i].getName());
        }
        for (int i = 0; i < parameters.length; ++i) {
            logger.debug(parameters[i].toString());
        }

        FastClass serviceFastClass = FastClass.create(serviceClass);

        int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
        return serviceFastClass.invoke(methodIndex, serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("server caught exception :" + cause);
        ctx.close();
    }

}
