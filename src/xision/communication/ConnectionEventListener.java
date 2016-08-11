package xision.communication;

/**
 * Created by Connor on 11/08/2016.
 */
public interface ConnectionEventListener<T extends Message>{

    void onMessage(Object source, T message);

}
