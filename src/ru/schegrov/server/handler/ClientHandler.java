package ru.schegrov.server.handler;

import java.net.Socket;
import java.util.Objects;

/**
 * Created by ramon on 24.10.2016.
 */
public abstract class ClientHandler implements Runnable {

    protected final Socket socket;

    protected ClientHandler(Socket socket) {
        Objects.requireNonNull(socket);
        this.socket = socket;
    }

//    public abstract void handle() throws IOException;
}
