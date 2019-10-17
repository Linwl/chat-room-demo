package com.linwl.chatroomdemo.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 15:30
 * @description ：
 * @modified By：
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel inComing = ctx.channel();//获得客户端通道
        //通知其他客户端有新人进入
        for (Channel channel : channels){
            if (channel != inComing)
                channel.writeAndFlush("[欢迎: " + inComing.remoteAddress() + "] 进入聊天室！\n");
        }

        channels.add(inComing);//加入队列
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel outComing = ctx.channel();//获得客户端通道
        //通知其他客户端有新人进入
        for (Channel channel : channels){
            if (channel != outComing)
                channel.writeAndFlush("[再见: ]" + outComing.remoteAddress() + " 离开聊天室！\n");
        }

        channels.remove(outComing);//加入队列
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel inComing = ctx.channel();
        for (Channel channel : channels){
            if (channel != inComing)
            {
                channel.writeAndFlush("[用户" + inComing.remoteAddress() + " 说：]" + msg + "\n");
            }
            else {
                channel.writeAndFlush("[我说：]" + msg + "\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inComing = ctx.channel();
        System.out.println("[" + inComing.remoteAddress() + "]: 在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inComing = ctx.channel();
        System.out.println("[" + inComing.remoteAddress() + "]: 离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel inComing = ctx.channel();
        System.out.println(inComing.remoteAddress() + "通讯异常！");
        ctx.close();
    }
}
