package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

/**
 * @program: nettystudy
 * @description: echo客户端运行端
 * @author: zhaoxudong
 * @create: 2020-02-02 21:28
 **/
public class EchoClientServer {

    private int port;
    private String host;


    public EchoClientServer(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            // 处理客户端的服务框架
            Bootstrap bootstrap = new Bootstrap();
            // 设置请求端口，channel
            bootstrap.group(eventLoopGroup)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer() {
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClient.EchoClientHandler());
                        }
                    });
            // 异步开启客户端，阻塞等待结果
            ChannelFuture channelFuture = bootstrap.connect().sync();
            // 处理完成，关闭通知
            channelFuture.channel().closeFuture().sync();


        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EchoClientServer echoClientServer = new EchoClientServer(8080, "127.0.0.1");
        echoClientServer.start();
    }


}
