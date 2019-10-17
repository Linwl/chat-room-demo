package com.linwl.chatroomdemo.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 16:20
 * @description ：
 * @modified By：
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //直接输出消息
        System.out.println(s);
    }

}
