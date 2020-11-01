package com.dk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * AIO-Future
 */
public class Server {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //创建一个AsynchronousServerSocketChannel
        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        //绑定地址
        asynchronousServerSocketChannel.bind(new InetSocketAddress("127.0.0.1",8585));
        while(true) {
            //Future方式开始监听
            System.out.println("开始监听8585");
            Future<AsynchronousSocketChannel> future = asynchronousServerSocketChannel.accept();
            AsynchronousSocketChannel asynchronousSocketChannel = future.get();//阻塞的
            System.out.println("接收到请求...");
            ByteBuffer dst = ByteBuffer.allocate(1024);
            //循环读取数据
            while (asynchronousSocketChannel.isOpen()){
                Future<Integer> read = asynchronousSocketChannel.read(dst);
                Integer integer = read.get();
                if(integer>0){
                    System.out.println(new String(dst.array(),0,integer,"utf-8"));
                    dst.clear();
                }
            }
        }
    }
}
