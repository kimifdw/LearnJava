package com.kimi.netty.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class BootstrapClient {

    /**
     * 创建client
     */
    public void create() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        // 创建并connect the new client channels
        bootstrap.group(group)
                // 异步socket channel
                .channel(NioSocketChannel.class)
                // 同步socket channel
//                .channel(OioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        System.out.println("收到的数据" + byteBuf.toString(CharsetUtil.UTF_8));
                    }
                });
        // 连接remote hosts
        ChannelFuture channelFuture = bootstrap.connect(
                new InetSocketAddress("www.baidu.com.cn", 80));
        // 同步时处理
//        channelFuture.syncUninterruptibly();
        // 添加监听
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            if (channelFuture1.isSuccess()) {
                System.out.println("连接已建立！");
            } else {
                System.err.println("连接建立失败！");
                channelFuture1.cause().printStackTrace();
            }
        });
    }


    public void createWithAttr() {
        AttributeKey<Integer> id = AttributeKey.newInstance("ID");

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        System.out.println("接收到的数据：" + byteBuf.toString(CharsetUtil.UTF_8));
                    }

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        bootstrap.attr(id, 123456);
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("www.sina.com.cn", 80));
        channelFuture.syncUninterruptibly();
    }

    public void createWithDatagramChannel() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup())
                .channel(OioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {

                    }
                });
        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(0));
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            if (channelFuture1.isSuccess()) {
                System.out.println("数据绑定");
            } else {
                System.err.println("数据绑定失败！");
                channelFuture1.cause().printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        BootstrapClient bootstrapClient = new BootstrapClient();
        bootstrapClient.create();
    }
}
