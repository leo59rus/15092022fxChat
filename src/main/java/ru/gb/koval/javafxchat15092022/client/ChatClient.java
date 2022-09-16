package ru.gb.koval.javafxchat15092022.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static jdk.internal.misc.InnocuousThread.newThread;

public class ChatClient {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final ChatController controller;

    public ChatClient(ChatController controller) {
        this.controller = controller;
    }
    public void openConnection() throws IOException {
        socket = new Socket("localhost",8189);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
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

    private void readMessages() throws IOException {
        while(true){
           final String message = in.readUTF();
           if("/end".equals(message)){
               break;
           }
           controller.addMessage(message);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
