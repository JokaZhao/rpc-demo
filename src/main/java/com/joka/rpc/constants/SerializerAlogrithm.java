package com.joka.rpc.constants;

import lombok.Getter;

/**
 * Created on 2019/7/30 18:50.
 *
 * @author zhaozengjie
 * Description : 序列化枚举类
 */
public enum SerializerAlogrithm {

    JSON((byte) 1);

    @Getter
    private byte id;


    SerializerAlogrithm(byte id) {
        this.id = id;
    }
}
