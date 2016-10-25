package ru.schegrov.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramon on 25.10.2016.
 */
public abstract class AbstractCommand implements Command, Serializable {

    private String key;
    private List<String> arguments = new ArrayList<>();

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
