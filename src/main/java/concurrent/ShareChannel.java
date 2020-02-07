package concurrent;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @program: nettystudy
 * @description: 共享channel代码示范
 * @author: zhaoxudong
 * @create: 2020-02-06 21:48
 **/
@ChannelHandler.Sharable
public class ShareChannel {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置当前serverChannel的eventLoopGroup 以及 子channel的对应类型
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    // 存放子channel的futrue
                    ChannelFuture connectFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("Received data");
                                    }
                                });
                        //================使用servechannel连接方法生成的eventLoop达到共享
                        bootstrap.group(ctx.channel().eventLoop());
                        //====================
                        connectFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
                    }

                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.err.println("输入");
                    }


                });
        ChannelFuture bind = serverBootstrap.bind(new InetSocketAddress(8080));
        bind.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });

    }
}
