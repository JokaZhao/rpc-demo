package com.joka.rpc.serialize;

import com.joka.rpc.protocol.AbstractPacket;
import com.joka.rpc.serialize.impl.JsonSerializer;

/**
 * Created on 2019/7/30 18:00.
 *
 * @author zhaozengjie
 * Description : 序列化基础接口
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    Byte getSerializerAlogrithm();

    /**
     * 序列化
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
