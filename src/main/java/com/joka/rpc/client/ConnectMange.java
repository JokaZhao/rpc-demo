package com.joka.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created on 2019/8/2 10:52.
 *
 * @author zhaozengjie
 * Description : 连接管理器
 */
public class ConnectMange {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private volatile static ConnectMange connectManage;

    private CopyOnWriteArrayList<Channel> connectedHandlers = new CopyOnWriteArrayList<>();

    public static ConnectMange getInstance() {
        if (connectManage == null) {
            synchronized (ConnectMange.class) {
                if (connectManage == null) {
                    connectManage = new ConnectMange();
                }
            }
        }
        return connectManage;
    }

    public Channel choose(){
        if (connectedHandlers.size() == 0){
            System.out.println("Not provider support.First time Init");
            Channel channel = NettyClient.init();
            if (channel!=null){
                return channel;
            }

            while (true){
                if (connectedHandlers.size()>0){
                    return connectedHandlers.get(0);
                }
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return connectedHandlers.get(0);

    }

    public void registry(Channel channel){

        connectedHandlers.add(channel);

    }

}
