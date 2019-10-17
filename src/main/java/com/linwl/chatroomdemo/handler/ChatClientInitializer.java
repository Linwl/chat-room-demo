package com.linwl.chatroomdemo.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 16:18
 * @description ：
 * @modified By：
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //用户定义的ChannelInitailizer加入到这个channel的pipeline上面去，这个handler就可以用于处理当前这个channel上面的一些事件
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("frame",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter())).addLast("decode",new StringDecoder(CharsetUtil.UTF_8)).addLast("encode",new StringEncoder(CharsetUtil.UTF_8)).addLast("handler",new ChatServerHandler());
    }
}
