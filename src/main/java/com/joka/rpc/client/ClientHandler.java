package com.joka.rpc.client;

import com.joka.rpc.protocol.AbstractPacket;
import com.joka.rpc.protocol.PacketCode;
import com.joka.rpc.protocol.RequestPacket;
import com.joka.rpc.protocol.ResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created on 2019/7/30 19:24.
 *
 * @author zhaozengjie
 * Description :
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");

        RequestPacket packet = new RequestPacket();


        ByteBuf requestBuf = PacketCode.INSTANCE.encode(ctx.alloc(), packet);


        // 2.写数据
        ctx.channel().writeAndFlush(requestBuf);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        AbstractPacket decode = PacketCode.INSTANCE.decode(byteBuf);

        if (decode instanceof ResponsePacket){
            ResponsePacket packet = (ResponsePacket) decode;

        }

    }
}
