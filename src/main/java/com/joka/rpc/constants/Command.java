package com.joka.rpc.constants;

import lombok.Getter;

/**
 * Created on 2019/7/30 19:23.
 *
 * @author zhaozengjie
 * Description :
 */
public enum Command {
    REQUEST((byte)1),
    RESPONSE((byte)2);

    @Getter
    private byte command;

    Command(byte command) {
        this.command = command;
    }
}
