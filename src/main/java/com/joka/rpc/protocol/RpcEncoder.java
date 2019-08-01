package com.joka.rpc.protocol;

import com.joka.rpc.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created on 2019/8/1 17:56.
 *
 * @author zhaozengjie
 * Description :
 */
public class RpcEncoder extends MessageToByteEncoder {


    private Class<?> clazz;

    public RpcEncoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (clazz.isInstance(o)){
            byte[] serialize = JsonSerializer.DEFAULT.serialize(o);
            byteBuf.writeInt(serialize.length);
            byteBuf.writeBytes(serialize);
        }

    }
}
