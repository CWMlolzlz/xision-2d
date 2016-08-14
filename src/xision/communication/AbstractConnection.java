package xision.communication;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Connor on 10/08/2016.
 */
public abstract class AbstractConnection implements Connection{ //E is the type of events to be dispatched

    private final Map<Class<? extends Message>,List<ConnectionEventListener<? extends Message>>> bindings = new HashMap<>();

    //T is the type of message to bind to
    public <M extends Message> void bind(Class<M> cluzz, ConnectionEventListener<? extends M> listener){
        bindings.putIfAbsent(cluzz, new ArrayList<>());
        bindings.get(cluzz).add(listener);
    }

    public <M extends Message> void unbind(Class<M> cluzz){
        bindings.remove(cluzz);
    }

    public <M extends Message> void unbind(Class<M> cluzz, ConnectionEventListener<? extends M> listener){
        if(!bindings.containsKey(cluzz)) return;
        bindings.get(cluzz).remove(listener);
    }

    public abstract void send(Message m);

    @SuppressWarnings("unchecked")
    protected  <M extends Message> void dispatch(ConnectionEvent<? extends M> event){
        Class<? extends Message> messageClass = event.getMessage().getClass();
        if(bindings.containsKey(messageClass)){
            for(ConnectionEventListener<?> listener : bindings.get(messageClass)){
                ConnectionEventListener<M> l = (ConnectionEventListener<M>)listener;
                l.onMessage(event.getSource(), event.getMessage());
            }
        }

    }
}
