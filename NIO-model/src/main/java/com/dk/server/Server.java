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
public class Server {
    public static void main(String[] args) {
        //创建一个List用来存储产生的socketChannel对象
        List<SocketChannel> socketChannels = new ArrayList<SocketChannel>();
        try {
            // 创建ServerSocketChannel通道，绑定监听端口为8585
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8585));
            while(true) {
                // 设置为非阻塞模式
                serverSocketChannel.configureBlocking(false);
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    System.out.println("接受到请求...");
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel放入集合。
                    socketChannels.add(socketChannel);
                    System.out.println("socketChannels-size:"+socketChannels.size());
                }
                //每次遍历所有的SocketChannl
                for (int x = 0;x < socketChannels.size();x++) {// 100000-99990 = 10
                    SocketChannel sc = socketChannels.get(x);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    try {
                        //读取数据
                        int read = sc.read(buffer);
                        //如果读取的数据长度大于0，说明读取到数据了。
                        if (read > 0) {
                            buffer.flip();
                            String str = new String(buffer.array(), 0, buffer.limit());
                            System.out.println("服务器端接受到数据：" + str);
                        }
                    }catch(IOException e){
                        System.out.println("关闭了一个连接");
                        socketChannels.remove(sc);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
