package ru.schegrov.server;

import ru.schegrov.server.handler.ClientHandler;
import ru.schegrov.server.handler.ClientHandlerFactory;
import ru.schegrov.server.handler.MessageHandlerFactory;
import ru.schegrov.utils.SaveStatisticTimerTask;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ramon on 21.10.2016.
 */
public class Server implements Closeable {

//    private int port;
    private ServerSocket serverSocket;
    private File directory;
    private int seconds;
    private ClientHandlerFactory factory;
    private ExecutorService executor;
    private ConcurrentHashMap<String, Integer> statistic;
    private volatile boolean closed;

    public ConcurrentHashMap<String, Integer> getStatistic() {
        return statistic;
    }

    public Server(int port, File directory, int seconds, ClientHandlerFactory factory, ExecutorService executor,
                  ConcurrentHashMap<String, Integer> statistic) throws IOException {

        Objects.requireNonNull(port);
        Objects.requireNonNull(directory);
        Objects.requireNonNull(seconds);
        Objects.requireNonNull(factory);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(statistic);

//        this.port = port;
        serverSocket = new ServerSocket(port);
        this.directory = directory;
        this.seconds = seconds;
        this.factory = factory;
        this.executor = executor;
        this.statistic = statistic;

        Timer timer = new Timer();
        timer.schedule(new SaveStatisticTimerTask(statistic), 0, seconds * 1000);
    }

    public void start() {
        try {
            while (!closed) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = factory.createHandler(socket, directory);
                executor.submit(handler);
            }
        } catch (SocketException e){
            e.printStackTrace();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() throws IOException {
        if (!closed) {
            closed = true;
            serverSocket.close();
        }
    }


    public static void main(String[] args) throws IOException {

        List<String> list = Arrays.asList(args);

        Integer port = Integer.valueOf(list.get(0));
        File directory = new File(list.get(1));
        Integer seconds = Integer.valueOf(list.get(2));
        MessageHandlerFactory factory = new MessageHandlerFactory();
        ExecutorService executor = Executors.newCachedThreadPool();
        ConcurrentHashMap<String, Integer> statistic = new ConcurrentHashMap<>();

        try (Server server = new Server(port, directory, seconds, factory, executor, statistic)) {

            server.start();

        } finally {
            executor.shutdown();
        }
    }




//        ru.schegrov.server.Server server = new ru.schegrov.server.Server(Integer.valueOf(args[2]));
//
//        try (ServerSocket serverSocket = new ServerSocket(Integer.valueOf(args[0]))) {
//            System.out.println("Сервер " + serverSocket);
//            File dir = new File(args[1]);
//
//            for (File file : dir.listFiles()){
//                server.getStatistic().put(file.getAbsolutePath(), 1);
//            }
//
//            System.out.println("Каталог определен " + dir.toPath().toAbsolutePath().normalize().toString());
//            while (true) {
//                Socket clientSocket= serverSocket.accept();
//                System.out.println("Клиент " + clientSocket);
//                new Thread(new ServerTask(server, clientSocket, dir)).start();
//            }
//        }

}
