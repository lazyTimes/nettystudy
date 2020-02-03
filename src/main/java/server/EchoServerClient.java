package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @program: nettystudy
 * @description: echoServer 的服务层实现
 * @author: zhaoxudong
 * @create: 2020-02-02 17:21
 **/
public class EchoServerClient {

    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        if(args.length > 1){
            System.err.println("server ");
        }
        final EchoServer echoServer = new EchoServer();
        // 创建事件驱动器 EventLoopGroup
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            // 创建server bootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 1. 指定使用NIO传输的渠道
            // 2. 指定端口
            // 3. 添加一个echoseverhandler 到子channel的chanelPipeline
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    .childHandler(new ChannelInitializer() {
                        // 很关键，需要使用pipeline加入事件来帮我们处理channel
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(echoServer);
                        }
                    });
            // 阻塞当前线程，异步绑定服务器
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            // 获取当前channel的closeFuture，阻塞直到线程完成
            channelFuture.channel().closeFuture().sync();

        }finally {
            // 关闭group 释放资源
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}
