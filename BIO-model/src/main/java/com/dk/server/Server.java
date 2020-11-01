package com.dk.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO模型服务器端
 */
public class Server {
    private static int port = 8585;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            //开始监听
            System.out.println("服务器开始监听，监听端口:" + port);
            while(true) {
                Socket socket = serverSocket.accept();//阻塞  等待客戶端連接
                System.out.println("接受一个客户端的请求.....");
                InputStream in = socket.getInputStream();
                int len = -1;
                byte[] buff = new byte[1024];
                while ((len = in.read(buff)) != -1) {//阻塞   等待客戶端發送数据
                    String str = new String(buff, 0, len);
                    System.out.println("读取到客户端的输入字符:" + str);
                }
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
