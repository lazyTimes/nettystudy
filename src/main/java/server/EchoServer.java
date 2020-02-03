package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 需要实现：ChannelInboundHandlerAdapter
 * ChannelHandler
 * @program: nettystudy
 * @description: Netty 服务端，用于处理客户端的I请求
 * @author: zhaoxudong
 * @create: 2020-02-02 16:00
 **/
public class EchoServer extends ChannelInboundHandlerAdapter {

    /**
     * 读取事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.err.println("Server receive" + in.toString(CharsetUtil.UTF_8));

    }

    /**
     * 最后一次读取完成之后，发送通知到channelHandler
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 读取的过程
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
