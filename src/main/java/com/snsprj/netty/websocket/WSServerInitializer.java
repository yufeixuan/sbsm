package com.snsprj.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author SKH
 * @date 2018-11-05 16:21
 **/
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        // webSocket 基于 http 协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());

        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // 以上用于支持http协议

        //
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 添加自定义handler
        pipeline.addLast(null);



    }
}
