package com.joka.rpc.protocol;

/**
 * Created on 2019/8/2 9:54.
 *
 * @author zhaozengjie
 * Description :
 */
public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}
