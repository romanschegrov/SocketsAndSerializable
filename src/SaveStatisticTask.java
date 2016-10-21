import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by ramon on 21.10.2016.
 */
public class SaveStatisticTask extends TimerTask {

    private Server server;
    private Logger logger;

    public SaveStatisticTask(Server server) {
        this.server = server;
        try {
            FileHandler handler = new FileHandler("statistic.log");
            handler.setFormatter(new SimpleFormatter());
            logger = Logger.getLogger("MyLog");
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Iterator<Map.Entry<String, Integer>> iterator = server.getStatistic().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            logger.info(String.format("Файл %s был скачен %d раз", entry.getKey(), entry.getValue()));
        }
    }
}
