package ru.schegrov.utils;

import ru.schegrov.server.Server;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by ramon on 21.10.2016.
 */
public class SaveStatisticTimerTask extends TimerTask {

    private ConcurrentHashMap<String, Integer> statistic;
    private Logger logger;

    public SaveStatisticTimerTask(ConcurrentHashMap<String, Integer> statistic) {
        this.statistic = statistic;
        try {
            FileHandler handler = new FileHandler("statistic.log");
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            logger = Logger.getLogger("MyLog");
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Iterator<Map.Entry<String, Integer>> iterator = statistic.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            logger.info(String.format("Файл %s был скачен %d раз", entry.getKey(), entry.getValue()));
        }
    }
}
