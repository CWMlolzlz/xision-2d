package xision.communication;

/**
 * Created by Connor on 11/08/2016.
 *
 * An interface designed to respond to {@link Message} objects.
 * This should be used to listen to events occurring along {@link Connection}s
 */
public interface ConnectionEventListener<T extends Message>{

    /**
     * The event called when a message is sent to the listener
     * @param source The object that sent the message
     * @param message The message send by the sender
     */
    void onMessage(Object source, T message);

}
