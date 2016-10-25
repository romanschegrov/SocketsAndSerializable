package ru.schegrov.server.handler;

import ru.schegrov.command.Command;
import ru.schegrov.message.FileMessage;
import ru.schegrov.message.Message;
import ru.schegrov.message.MessageType;
import ru.schegrov.message.TextMessage;

import java.io.*;
import java.net.Socket;

/**
 * Created by ramon on 24.10.2016.
 */
public class MessageClientHandler extends ClientHandler {

    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private File directory;

    protected MessageClientHandler(Socket socket, File directory) throws IOException {
        super(socket);

        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());

        this.directory = directory;
    }

//    @Override
//    public void handle() throws IOException {
//
//    }

    @Override
    public void run() {
        try {
            while (true) {
                Command command = (Command) in.readObject();
                Message message = null;
                switch (command.getKey().toUpperCase()) {
                    case "-L":
                        StringBuilder builder = new StringBuilder();
                        fileList(directory, builder);
                        message = new TextMessage();
                        message.setMessageType(MessageType.TEXT);
                        message.setData(builder.toString().getBytes());
                        break;
                    case "-F":
                        File file = new File(command.getArguments().get(0));
                        if (file.exists()){
                            byte[] buffer = new byte[(int) file.length()];
                            try (FileInputStream fis = new FileInputStream(file)) {
                                fis.read(buffer);
//                            System.out.println("Готово\nФайл скопирован успешно " + newFile.toPath().toAbsolutePath().normalize().toString());
                            }
                            message = new FileMessage();
                            message.setMessageType(MessageType.FILE);
                            message.setData(buffer);
                        } else {
                            String text = String.format("Файл %s не найден", command.getArguments().get(0));
                            message = new TextMessage();
                            message.setMessageType(MessageType.TEXT);
                            message.setData(text.getBytes());
                        }
                        break;
                    case "-H":
                        message = new TextMessage();
                        message.setMessageType(MessageType.TEXT);
                        message.setData(help().getBytes());
                        break;
                    default:
                        break;
                }
                out.writeObject(message);
                out.flush();
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fileList(File directory, StringBuilder builder) {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory())
                fileList(file, builder);
            else
                builder.append("\t" + file + "\n");
        }
    }

    private String help() {
        StringBuilder builder = new StringBuilder();
        builder.append("|----------------------------------------------|");
        builder.append("\n|\tСправка                                |");
        builder.append("\n|----------------------------------------------|");
        builder.append("\n|\t-h Показать справку                    |");
        builder.append("\n|\t-l Показать список файлов              |");
        builder.append("\n|\t-f filepath-with-filename Скачать файл |");
        builder.append("\n|\t-e Выйти                               |");
        builder.append("\n|----------------------------------------------|");
        return builder.toString();
    }
}
