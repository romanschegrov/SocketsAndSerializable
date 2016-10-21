import java.io.Serializable;

/**
 * Created by ramon on 21.10.2016.
 */
public class Message implements Serializable {

    private MessageType type;
    private byte[] data;
    private long length;

}
