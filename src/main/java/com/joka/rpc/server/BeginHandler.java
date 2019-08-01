package com.joka.rpc.server;

import com.joka.rpc.protocol.AbstractPacket;
import com.joka.rpc.protocol.PacketCode;
import com.joka.rpc.protocol.RequestPacket;
import com.joka.rpc.protocol.ResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created on 2019/7/30 19:39.
 *
 * @author zhaozengjie
 * Description :
 */
public class BeginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        AbstractPacket packet = PacketCode.INSTANCE.decode(requestByteBuf);

        if (packet instanceof RequestPacket){
            RequestPacket requestPacket = (RequestPacket) packet;
            ResponsePacket responsePacket = new ResponsePacket();
            ByteBuf responseByteBuf = PacketCode.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }else {

            System.out.println("unknow packet");
        }


    }


}