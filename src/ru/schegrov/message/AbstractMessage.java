package ru.schegrov.message;

import java.io.Serializable;

/**
 * Created by ramon on 25.10.2016.
 */
public abstract class AbstractMessage implements Message, Serializable {

    private MessageType messageType;
    private byte[] data;

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
