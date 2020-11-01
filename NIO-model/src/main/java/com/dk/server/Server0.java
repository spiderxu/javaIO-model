package com.dk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * NIO
 */
public class Server0 {
    public static void main(String[] args) throws InterruptedException {
        try {
            // 创建ServerSocketChannel通道，绑定监听端口为8585
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(8585));
            // 设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //开始监听
            System.out.println("开始监听....");
            while(true) {
                Thread.sleep(1000);
                //循环监听，非阻塞状态
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("socketChannel:" + socketChannel);
                //接收到请求
                if (socketChannel != null) {
                    System.out.println("接受到请求...");
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //读取数据？？？？
                    ByteBuffer dst = ByteBuffer.allocate(1024);
                    int len = socketChannel.read(dst);//非阻塞
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
