package com.whpu.socket.twoWayCommunicate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: xuzhi
 * @date: 2020/12/16 14:26
 */
public class Server {
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(8080);
            //创建一个ServerSocket在端口8080监听客户端请求
            Socket socket = null;
            try {
                socket = serverSocket.accept();

            } catch (Exception e) {
                System.out.println("ERROR" + e);
            }

            String line;
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Client:" + is.readLine());
            line = sin.readLine();

            while (!line.equals("bye")) {
                os.println(line);
                os.flush();
                System.out.println("Server:" + line);
                System.out.println("Client:" + is.readLine());

                line = sin.readLine();
            }

            os.close();
            is.close();
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }


    }
}
