package com.dk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AcceptPendingException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * AIO
 */
public class Server1 {
    public static void main(String[] args) throws Exception {
        //创建一个AsynchronousServerSocketChannel
       final  AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        //绑定地址
        asynchronousServerSocketChannel.bind(new InetSocketAddress("127.0.0.1",8585));
            System.out.println("开始监听8585");
            //开始监听  参数(null,回调处理器)
            asynchronousServerSocketChannel.accept(null,
                    new CompletionHandler<AsynchronousSocketChannel, Void>() {

                        @Override
                        public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Void attachment) {
                            //循环监听
                            asynchronousServerSocketChannel.accept(null,this);
                            // 接收到新的客户端连接时调用，result就是和客户端的连接对话，此时可以通过result和客户端进行通信
                            System.out.println("accept completed");
                            ByteBuffer dst = ByteBuffer.allocate(1024);
                            //循环读取数据
                            while (asynchronousSocketChannel.isOpen()){
                                Future<Integer> read = asynchronousSocketChannel.read(dst);
                                try {
                                    Integer integer = read.get();
                                    if (integer > 0) {
                                        System.out.println(new String(dst.array(), 0, integer, "utf-8"));
                                        dst.clear();
                                    }
                                }catch (Exception e)
                                {}
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) {
                            // accept失败时回调
                            System.out.println("accept failed");
                        }
                    });

            //这里主线程不会暂停等待，会继续执行。。。。。。
            //让程序暂停
            System.in.read();//主程序阻塞
    }
}
