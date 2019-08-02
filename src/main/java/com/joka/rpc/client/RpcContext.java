package com.joka.rpc.client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2019/8/2 10:06.
 *
 * @author zhaozengjie
 * Description :
 */
public class RpcContext {

    private static ConcurrentHashMap<String, RPCFuture> pendingRPC = new ConcurrentHashMap<>();

    public static RPCFuture getFuture(String requestId){
        return pendingRPC.get(requestId);
    }

    public static void add(String requestId,RPCFuture rpcFuture){
        pendingRPC.put(requestId,rpcFuture);
    }

    public static void remove(String requestId){
        pendingRPC.remove(requestId);
    }

}
