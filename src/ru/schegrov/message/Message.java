package ru.schegrov.message;

/**
 * Created by ramon on 21.10.2016.
 */
public interface Message {

    MessageType getMessageType();
    void setMessageType(MessageType messageType);
    byte[] getData();
    void setData(byte[] data);
}
