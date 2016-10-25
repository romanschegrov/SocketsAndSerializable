package ru.schegrov.client;

import ru.schegrov.command.Command;
import ru.schegrov.command.CommandBuilder;
import ru.schegrov.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ramon on 21.10.2016.
 */
public class Client {

    private File directory;

    private File getDirectory() {
        Scanner scanner = new Scanner(System.in);
        if (directory == null) {
            directory = setDirectory(scanner);
        }
        else {
            System.out.print("Текущий каталог " + directory.toPath().toAbsolutePath().normalize().toString() + ", хотите изменить (y/n):");
            while (scanner.hasNextLine()) {
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("Y")) {
                    directory = setDirectory(scanner);
                    break;
                } else if (answer.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.print("Текущий каталог " + directory.toPath().toAbsolutePath().normalize().toString() + ", хотите изменить (y/n):");
                }
            }
        }
        return directory;
    }

    private File setDirectory(Scanner scanner){
        System.out.print("Введите каталог для копирования файла (Например C:\\abc или ./abc или ../../abc):");
        while (scanner.hasNextLine()) {
            String pathname = scanner.nextLine();
            directory = new File(pathname);
            if (directory.exists() && directory.isDirectory()) {
                System.out.println("Каталог установлен " + directory.toPath().toAbsolutePath().normalize().toString());
                break;
            }
            System.out.println("Ошибка при вводе каталога '" + pathname + "'");
        }
        return directory;
    }


    public Client(String host, int port) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            System.out.print("Введите команду (введите -h для справки): ");

            Scanner sis = new Scanner(System.in);
            while (sis.hasNextLine()) {
                String commandLine = sis.nextLine();

                if (commandLine.equalsIgnoreCase("-E")) break;

                Command command = CommandBuilder.newCommand(commandLine);

                out.writeObject(command);
                out.flush();

                Message message = (Message) in.readObject();
                switch (message.getMessageType()){
                    case TEXT:
                        String text = new String(message.getData(), 0, message.getData().length);
                        System.out.println(text);
                        break;
                    case FILE:
                        File file = new File(command.getArguments().get(0));
                        File newFile = new File(getDirectory(), file.getName());

                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
                            fos.write(message.getData());
                            fos.flush();
                            System.out.println(String.format("Файл %s скопирован успешно",newFile.toPath().toAbsolutePath().normalize().toString()));
                        }
                        break;
                    default:
                        break;
                }
                System.out.print("Введите команду (введите -h для справки): ");
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<String> list = Arrays.asList(args);
        Client client = new Client(list.get(0), Integer.valueOf(list.get(1)));
    }
}
