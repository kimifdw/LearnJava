package com.kimi.netty.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * socket同步
 * Created by kimiyu on 2017/4/28.
 */
public class PlainOloServer {

    public void serve(int port) throws IOException {
        // socket通讯[同步]，绑定端口
        final ServerSocket socket = new ServerSocket(port);
        try {
            for (; ; ) {
                final Socket client = socket.accept();
                System.out.println("接收客户端连接：" + client);
                new Thread(() -> {
                    OutputStream out;
                    try {
                        out = client.getOutputStream();
                        out.write("Hi:\r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        client.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("读取消息异常:" + ex.getMessage());
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.out.println("客户端关闭异常:" + ex.getMessage());
                        }
                    }
                }).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void serveWithNetty(int port) throws IOException, InterruptedException {
        final ByteBuf byteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi:\r\n", CharsetUtil.UTF_8));
        EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    // block channel
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(byteBuf.duplicate())
                                            .addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
