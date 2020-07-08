package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static Socket socket;
    public static ServerSocket server;

    public static void main(String[] args) {
        Server server = new Server();
    }
    public Server() {
//        ServerSocket server = null;
//        Socket socket = null;

        try {
            server = new ServerSocket(9999);
            System.out.println("Пока всё нормально...");
            socket = server.accept();
            System.out.println("Подключение установлено.");
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //или пишем внизу метод
            Scanner scanner = new Scanner(System.in);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String msg = scanner.nextLine();
                        out.println(msg);
                    }
                }
            }).start();
            while (true) {
                String msg = in.nextLine();
                if (msg.equals("/end")) break;
                System.out.println("Клиент: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
                System.out.println("Конец. (Клиент набрал '/end'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


