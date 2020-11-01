package com.dk.server;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server1 {
    public static void main(String[] args) throws InterruptedException {
        try {
            // 创建ServerSocketChannel通道，绑定监听端口为8585
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8585));
            serverSocketChannel.configureBlocking(false);//非阻塞
            // 注册选择器,设置选择器选择的操作类型
            Selector selector = Selector.open();
            //将serverSocketChannel注册在selector中
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            while(true) {
                //选择事件,这里会阻塞
                selector.select();//如果没有任何事件可以处理，则该方法处于阻塞状态
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){//这里的事件都是已经筛选好的可以处理的事件
                    System.out.println("处理事件");
                    System.out.println("----"+selectionKeys.size()+"----");
                    SelectionKey next = iterator.next();
                    iterator.remove();//删除key防止重复处理
                    if(next.isReadable()){
                        System.out.println("连接请求事件");
                        //接收到数据读取事件
                        SocketChannel channel = (SocketChannel)next.channel();
                        ByteBuffer dest = ByteBuffer.allocate(1024);
                        int read = channel.read(dest);
                        System.out.println(new String(dest.array(),0,read));
                    }else  if(next.isAcceptable()){
                        System.out.println("数据读取事件");
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        //接收到请求，将连接注册到选择器中，并且设置监听类型为read
                        accept.register(selector,SelectionKey.OP_READ);
                    }else if(next.isWritable()){
                        System.out.println("写数据事件");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
