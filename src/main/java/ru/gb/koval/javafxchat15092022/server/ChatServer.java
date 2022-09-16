package ru.gb.koval.javafxchat15092022.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private final List<ClientHandler> clients;//те клиенты которые
    // подключились к серверу



    public ChatServer() {
        this.clients = new ArrayList<>();

    }

    //создаем в методе ран серверный сокет и ждем подключения
    // клиента/ов
    //
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(8189);
             AuthService authService = new InMemoryAuthService()) {
            while(true){
            System.out.println("Ожидаем подключения..");
            final Socket socket = serverSocket.accept();

            new ClientHandler(socket, this, authService);
            System.out.println("Клиент подключился");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}
