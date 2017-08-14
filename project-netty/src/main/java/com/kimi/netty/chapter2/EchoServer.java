package com.kimi.netty.chapter2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * netty server端
 * Created by kimiyu on 2017/4/25.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage：" + EchoServer.class.getSimpleName() + "<port>");
        }

        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            // 1. 初始化ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 2.
            serverBootstrap.group(eventExecutors)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(echoServerHandler);
                        }
                    });
            // 同步等待绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            // 同步等待关闭完成并释放所有线程
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully().sync();
        }
    }
}
