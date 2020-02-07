package concurrent;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: nettystudy
 * @description: EventLoop 实验
 * @author: zhaoxudong
 * @create: 2020-02-06 19:43
 **/
public class EventLoopTest {

    public static void main(String[] args) {
        Channel channel = new NioServerSocketChannel();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        eventLoopGroup.register(channel);
        ScheduledFuture scheduledFuture = eventLoopGroup.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        System.err.println("调度开始");
                    }
                }
        , 10,10, TimeUnit.SECONDS);
    }
}
