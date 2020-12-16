package com.whpu.socket.sendmessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: xuzhi
 * @date: 2020/12/16 13:59
 * 服务端
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=null;
        BufferedReader in=null;

        try {
            /**
             * TCP的服务端要先监听一个端口，一般是先调用bind函数，给
             * 这个Socket赋予一个IP地址和端口。
             */
            serverSocket=new ServerSocket(8080);

            //服务端阻塞，等待客户端连接
            Socket socket = serverSocket.accept();

            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (serverSocket!=null){
                serverSocket.close();
            }
        }
    }
}
