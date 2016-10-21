import java.io.File;
import java.net.Socket;

/**
 * Created by ramon on 21.10.2016.
 */
public class ClientSocket implements Runnable {

    private Server server;
    private Socket socket;
    private File dir;

    public ClientSocket(Server server, Socket socket, File dir) {
        this.server = server;
        this.socket = socket;
        this.dir = dir;
    }

    @Override
    public void run() {

    }
}
