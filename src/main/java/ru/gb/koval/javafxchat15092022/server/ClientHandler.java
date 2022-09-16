package ru.gb.koval.javafxchat15092022.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private AuthService authService;
    private Socket socket;

    //класс чатсервер знает всё о клиентах
    private ChatServer server;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;

    public ClientHandler(Socket socket, ChatServer server,
                         AuthService authService) {
        try {
            this.server = server;
            this.socket = socket;
            this.authService = authService;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    readMessages();
                } finally {
                    closeConnection();
                }
            }).start();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    private void closeConnection() {
        sendMessage("/end");
        if(in!= null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(out!=null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() {
        while(true){
            try {
                final String message = in.readUTF();
                if("/end".equals(message)){
                    break;
                }
                server.broadcast(nick + ": " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

