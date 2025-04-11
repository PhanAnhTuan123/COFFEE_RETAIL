package com.example.coffeeretail.server;

import java.net.ServerSocket;
import java.net.Socket;

public class CafeServer {
    private static final int PORT = 1010;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                // Mỗi kết nối dùng một thread riêng
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
