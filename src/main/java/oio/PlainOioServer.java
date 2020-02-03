package oio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: nettystudy
 * @description: old io 阻塞io多线程
 * @author: zhaoxudong
 * @create: 2020-02-03 21:02
 **/
public class PlainOioServer {

    /**
     * 服务运行方法
     * @param port 端口号
     */
    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        final Socket accept = serverSocket.accept();
        try{
            while(true){
                System.err.println("Server connect from"+accept);
                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out = null;
                        try {
                            out = accept.getOutputStream();
                            out.write("Hello!".getBytes());
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if(out != null){
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
