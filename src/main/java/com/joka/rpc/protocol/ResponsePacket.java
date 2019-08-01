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
public class ResponsePacket extends AbstractPacket{

    private String requestId;
    private String error;
    private Object result;

    @Override
    public Byte getCommand() {
        return Command.RESPONSE.getCommand();
    }


}
