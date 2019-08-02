package com.joka.rpc.server;

import com.joka.rpc.protocol.RequestPacket;
import com.joka.rpc.protocol.ResponsePacket;
import com.joka.rpc.protocol.RpcDecoder;
import com.joka.rpc.protocol.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/7/30 19:26.
 *
 * @author zhaozengjie
 * Description :
 */
public class NettyServer {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int BEGIN_PORT = 8000;

    private Map<String, Object> handlerMap = new HashMap<>();

    public  void start() throws Exception {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                .addLast(new RpcDecoder(RequestPacket.class))
                                .addLast(new RpcEncoder(ResponsePacket.class))
                                .addLast(new ServerRpcHandler(handlerMap));
                    }
                });
        bind(serverBootstrap, BEGIN_PORT);
    }


    private static void bind(final ServerBootstrap serverBootstrap, final int port) throws InterruptedException {
        ChannelFuture sync = serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.out.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        }).sync();

    }

    public NettyServer addService(String interfaceName, Object serviceBean) {
        if (!handlerMap.containsKey(interfaceName)) {
            System.out.println("Loading service: {}"+ interfaceName);
            handlerMap.put(interfaceName, serviceBean);
        }

        return this;
    }
}
