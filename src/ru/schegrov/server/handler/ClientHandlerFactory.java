package ru.schegrov.server.handler;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ramon on 24.10.2016.
 */
public interface ClientHandlerFactory {

    public ClientHandler createHandler(Socket socket, File directory) throws IOException;

}
