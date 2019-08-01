package com.joka.rpc.protocol;

import com.joka.rpc.constants.Command;
import com.joka.rpc.serialize.Serializer;
import com.joka.rpc.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/7/30 17:58.
 *
 * @author zhaozengjie
 * Description : 基础协议包
 */
public class PacketCode {

    //魔数
    private static final Integer MAGIC_NUMBER = 0x12345678;

    public static final PacketCode INSTANCE = new PacketCode();

    private final Map<Byte,Class<? extends AbstractPacket>> packetTypeMap;

    private final Map<Byte, Serializer> serializerMap;

    private PacketCode(){
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.REQUEST.getCommand(),RequestPacket.class);
        packetTypeMap.put(Command.RESPONSE.getCommand(),ResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(),serializer);
    }


    public ByteBuf encode(ByteBufAllocator byteBufAllocator, AbstractPacket packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public AbstractPacket decode(ByteBuf byteBuf){
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends AbstractPacket> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends AbstractPacket> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
