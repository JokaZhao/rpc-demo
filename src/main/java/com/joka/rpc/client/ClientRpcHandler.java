package com.joka.rpc.client;

import com.joka.rpc.protocol.ResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2019/8/2 9:50.
 *
 * @author zhaozengjie
 * Description :
 */
public class ClientRpcHandler extends SimpleChannelInboundHandler<ResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(ClientRpcHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponsePacket response) throws Exception {
        String requestId = response.getRequestId();
        RPCFuture rpcFuture = RpcContext.getFuture(requestId);
        if (rpcFuture != null) {
            try {
                rpcFuture.done(response);
            } finally {
                RpcContext.remove(requestId);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client caught exception :"+ cause);
        ctx.close();
    }

}
