package com.joka.rpc.protocol;

import lombok.Data;

/**
 * Created on 2019/7/30 17:48.
 *
 * @author zhaozengjie
 * Description : 基础包
 */
@Data
public abstract class AbstractPacket {

    private Byte version = 1;

    public abstract Byte getCommand();

}
