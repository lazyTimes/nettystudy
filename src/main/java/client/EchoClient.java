package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @program: nettystudy
 * @description: Echo客户端
 * @author: zhaoxudong
 * @create: 2020-02-02 20:54
 **/
public class EchoClient {

    /**
     * 使用内部类实现一个channelHandler
     */
    public static class EchoClientHandler extends SimpleChannelInboundHandler {

        /**
         * 当从服务器收到一条消息的时候调用
          * @param ctx
         * @param msg
         * @throws Exception
         */
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf res = (ByteBuf) msg;
            System.err.println(res.toString(CharsetUtil.UTF_8));
        }


        /**
         * 被通知channel是活跃的时候，发送一条消息
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        }

        /**
         * 当出现异常的时候调用此方法
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


    public static void main(String[] args) {

    }

}
