package ru.gb.koval.javafxchat15092022.server;

import java.io.Closeable;

public interface AuthService extends Closeable {
    String getNickByLoginPassword(String login, String password);

}
