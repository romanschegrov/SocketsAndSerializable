package ru.schegrov.command;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ramon on 25.10.2016.
 */
public class CommandBuilder {
    public static Command newCommand(String commandLine){
        List<String> list = Arrays.asList(commandLine.split(" "));
        Command command;
        switch (list.get(0).toUpperCase()){
            case "-L":
                command = new ListCommand();
                break;
            case "-F":
                command = new FileCommand();
                command.getArguments().addAll(list.subList(1, list.size()));
                break;
            default:
                command = new HelpCommand();
                break;

        }
        return command;
    }
}
