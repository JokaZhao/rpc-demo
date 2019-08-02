package com.joka.rpc.client;

import com.joka.rpc.protocol.RequestPacket;
import com.joka.rpc.protocol.ResponsePacket;
import com.joka.rpc.protocol.RpcDecoder;
import com.joka.rpc.protocol.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created on 2019/8/2 11:15.
 *
 * @author zhaozengjie
 * Description :
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new RpcEncoder(RequestPacket.class));
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        cp.addLast(new RpcDecoder(ResponsePacket.class));
        cp.addLast(new ClientRpcHandler());
    }
}
