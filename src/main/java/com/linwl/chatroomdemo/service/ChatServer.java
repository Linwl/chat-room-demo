package com.linwl.chatroomdemo.service;

import com.linwl.chatroomdemo.handler.ChatServerInitialize;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.text.MessageFormat;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 15:15
 * @description ：
 * @modified By：
 */
public class ChatServer {

    /**
     * 端口号
     */
    private int port;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(10);
    private EventLoopGroup workerGroup = new NioEventLoopGroup(20);

    public ChatServer(int port) {
        this.port = port;
    }


    public void start()
    {
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChatServerInitialize()).option(ChannelOption.SO_BACKLOG,1024).option(ChannelOption.SO_KEEPALIVE,true);
            //绑定监听端口，调用sync同步阻塞方法等待绑定操作完成，完成后返回ChannelFuture类似于JDK中Future
            ChannelFuture future = bootstrap.bind(port).sync();
        }
        catch (InterruptedException e)
        {
            System.out.println(MessageFormat.format("服务启动异常:{0}",e.getMessage()));
        }
        finally {
            //优雅退出，释放线程池资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
