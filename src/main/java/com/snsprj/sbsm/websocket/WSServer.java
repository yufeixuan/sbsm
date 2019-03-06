package com.snsprj.sbsm.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author SKH
 * @date 2018-11-05 16:09
 **/
public class WSServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup parentGroup = new NioEventLoopGroup();

        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(parentGroup,childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {

            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }


    }
}
