package com.snsprj.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 *
 * TextWebSocketFrame：在netty中专门给webSocket处理文本的对象，frame是消息的载体
 *
 * @author SKH
 * @date 2018-11-05 17:11
 *
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
            TextWebSocketFrame textWebSocketFrame) throws Exception {

    }
}
