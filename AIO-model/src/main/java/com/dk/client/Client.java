package com.dk.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入客户端编号:");
        int no = sc.nextInt();
        AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open();
        asynchronousSocketChannel.connect(new InetSocketAddress("127.0.0.1",8585));
        ByteBuffer buff = ByteBuffer.allocate(1024);
        String str = "";
        while(true){
            System.out.println("客户端"+no+"请输入要发送的内容:");
            str = sc.next();
            if(str.equalsIgnoreCase("quit"))
                break;
            byte[] bytes = (no+":"+str).getBytes();
            buff = ByteBuffer.allocate(bytes.length);
            buff.put(bytes);
            buff.flip();
            asynchronousSocketChannel.write(buff);
        }
    }
}
