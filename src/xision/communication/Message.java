package xision.communication;

import java.io.Serializable;

/**
 * Created by Connor on 8/08/2016.
 *
 * The low level interface to encapsulate data sent between
 * connections. This can be used internally or sent across network
 *
 * Note: When extending this class be careful when adding Objects
 * as fields. This class must be serializable and therefore any
 * contained fields must also be marked Serializable.
 */
public interface Message extends Serializable{

    /*public enum TransmissionMode{
        ECHO,
        NO_RETURN,
        NO_RETURN_BROADCAST,
        BROADCAST;
    }

    public abstract TransmissionMode getTransmissionMode();*/

}
