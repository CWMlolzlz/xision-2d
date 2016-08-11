package xision.communication;

/**
 * Created by Connor on 10/08/2016.
 */
public interface Connection{

    //T is the type of message to bind to
    <M extends Message> void bind(Class<M> cluzz, ConnectionEventListener<? extends M> listener);

    <M extends Message> void unbind(Class<M> cluzz);

    <M extends Message> void unbind(Class<M> cluzz, ConnectionEventListener<? extends M> listener);

    void connect();

    void send(Message m);

    <M extends Message> void dispatch(ConnectionEvent<? extends M> event);
}
