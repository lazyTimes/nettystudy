package idle;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @program: nettystudy
 * @description: 心跳检测实现
 * @author: zhaoxudong
 * @create: 2020-02-07 20:02
 **/
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatHandler());
    }

    // ← -- 实现userEven t-Triggered()方法以发送心跳消息
    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        // 发送到远程节点的心跳消息 
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            // 如果是心跳的事件
            if(evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else{
                // 如果不是进行传递
                super.userEventTriggered(ctx, evt);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class)
                .group(eventLoopGroup)
                .localAddress(new InetSocketAddress(8888))
        .childHandler(new IdleStateHandlerInitializer());
        serverBootstrap.bind().sync();
    }
}
