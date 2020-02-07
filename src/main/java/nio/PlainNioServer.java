package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: nettystudy
 * @description: Nio server 异步阻塞模型
 * @author: zhaoxudong
 * @create: 2020-02-03 21:50
 **/
public class PlainNioServer {


    /**
     * 异步nio处理
     * @param port
     */
    public void server(int port) throws IOException {
        // 开启一个nio的channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 配置
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        // 给服务器绑定address
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        socket.bind(inetSocketAddress);
        // 打开selector 处理channel
        Selector selector = Selector.open();
        // 把serverSocket 注册到selector 接收连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer.wrap("Hello world".getBytes());

        // 开启线程
        for (;;){
            try{
                // 等待处理新事件
                selector.select();

            }catch (IOException e){
                e.printStackTrace();
                break;
            }
            // 获取所有接收事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 获取迭代器
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                try{
                    // 检查事件是否是一个新的已经就绪可以被接受的连接
                    if(next.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        // 接受客户端，并将它注册到选择器
                        accept.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);
                        System.out.println(
                                "Accepted connection from "+ channel);


                    }
                    // 检查套接字是否已经准备好写数据
                    if(next.isWritable()){
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                        while(byteBuffer.hasRemaining()){
                            // 将数据连接到客户端
                            if(channel.write(byteBuffer) == 0){
                                break;
                            }
                            channel.close();

                        }
                    }
                }catch (IOException ex){
                    next.cancel();
                    try{
                        next.channel().close();
                    }catch (Exception e){

                    }
                }

            }

        }

    }

    public static void main(String[] args) throws IOException {
        new PlainNioServer().server(7777);
    }

}
