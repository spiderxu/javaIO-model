package com.whpu.socket.sendmessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author: xuzhi
 * @date: 2020/12/16 13:58
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        Socket socket=null;
        PrintWriter out=null;
        try {
            socket=new Socket("127.0.0.1",8080);
            out=new PrintWriter(socket.getOutputStream(),true);
            out.println("Hello,World");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (out!=null){
                out.close();
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
