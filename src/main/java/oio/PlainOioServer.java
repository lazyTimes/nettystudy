package oio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        try{
            for(int x = 0; x < 9; x++){
                final Socket socketClient = serverSocket.accept();
                System.err.println("Server connect from"+socketClient);
                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out = null;
                        try {
                            out = socketClient.getOutputStream();
                            out.write("测试".getBytes("utf-8"));
                            out.flush();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                            String s = bufferedReader.readLine();
                            System.err.println(s);
                            bufferedReader.close();
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
                }).start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        new PlainOioServer().server(9999);
    }
}
