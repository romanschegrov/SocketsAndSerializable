import java.io.IOException;

/**
 * Created by ramon on 21.10.2016.
 */
public class Client {

    private String host;
    private String port;

    public Client(String host, String port) {
        this.host = host;
        this.port = port;
    }

    //хост
    //порт
    public static void main(String[] args) throws IOException {
        new Client(args[0], args[1]);
    }
}
