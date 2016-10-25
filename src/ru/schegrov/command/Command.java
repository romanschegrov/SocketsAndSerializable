package ru.schegrov.command;

import java.util.List;

/**
 * Created by ramon on 25.10.2016.
 */
public interface Command {
    String getKey();
    List<String> getArguments();
    void setArguments(List<String> list);
}
