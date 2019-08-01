package com.joka.rpc.protocol;

import com.joka.rpc.constants.Command;
import lombok.Data;

/**
 * Created on 2019/7/30 19:24.
 *
 * @author zhaozengjie
 * Description :
 */
@Data
public class RequestPacket extends AbstractPacket{

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    @Override
    public Byte getCommand() {
        return Command.REQUEST.getCommand();
    }
}
