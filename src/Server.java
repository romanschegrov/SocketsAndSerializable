import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ramon on 21.10.2016.
 */
public class Server {

    private ConcurrentHashMap<String, Integer> statistic;

    public ConcurrentHashMap<String, Integer> getStatistic() {
        return statistic;
    }

    public Server(int milliseconds) throws FileNotFoundException {
        this.statistic = new ConcurrentHashMap<>();
        Timer timer = new Timer();
        timer.schedule(new SaveStatisticTask(this), milliseconds, milliseconds);
    }

    //порт
    //каталог
    //милисекунды для сброса статистики
    public static void main(String[] args) throws IOException {
        Server server = new Server(Integer.valueOf(args[2]));

        try (ServerSocket serverSocket = new ServerSocket(Integer.valueOf(args[0]))) {
            System.out.println("Сервер " + serverSocket);
            File dir = new File(args[1]);

            for (File file : dir.listFiles()){
                server.getStatistic().put(file.getAbsolutePath(), 1);
            }

            System.out.println("Каталог определен " + dir.toPath().toAbsolutePath().normalize().toString());
            while (true) {
                Socket clientSocket= serverSocket.accept();
                System.out.println("Клиент " + clientSocket);
                new Thread(new ClientSocket(server, clientSocket, dir)).start();
            }
        }
    }
}
