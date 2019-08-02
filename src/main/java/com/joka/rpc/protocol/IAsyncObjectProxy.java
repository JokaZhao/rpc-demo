package com.joka.rpc.protocol;

import com.joka.rpc.client.RPCFuture;

/**
 * Created on 2019/8/2 10:33.
 *
 * @author zhaozengjie
 * Description :
 */
public interface IAsyncObjectProxy {

    RPCFuture call(String funcName, Object... args);

}
