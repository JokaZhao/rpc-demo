package com.joka.rpc.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.joka.rpc.constants.SerializerAlogrithm;
import com.joka.rpc.protocol.AbstractPacket;
import com.joka.rpc.serialize.Serializer;

/**
 * Created on 2019/7/30 18:49.
 *
 * @author zhaozengjie
 * Description : json序列化器
 */
public class JsonSerializer implements Serializer {
    @Override
    public Byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON.getId();
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
