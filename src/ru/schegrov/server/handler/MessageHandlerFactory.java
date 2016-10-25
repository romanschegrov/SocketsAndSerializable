package ru.schegrov.server.handler;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ramon on 24.10.2016.
 */
public class MessageHandlerFactory implements ClientHandlerFactory {

    @Override
    public ClientHandler createHandler(Socket socket, File directory) throws IOException {
        return new MessageClientHandler(socket, directory);
    }
}
