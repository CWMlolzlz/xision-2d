package xision.communication;

/**
 * Created by Connor on 10/08/2016.
 */
public class ConnectionEvent<M extends Message>{
    private final M message;
    private final Object source;

    public ConnectionEvent(Object source, M message){
        this.message = message;
        this.source = source;
    }
    public Object getSource(){return source;}
    public M getMessage(){
        return message;
    }
}
