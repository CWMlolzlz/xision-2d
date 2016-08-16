package messages;

import xision.communication.Message;

/**
 * Created by Connor on 11/08/2016.
 */
public class SetPlayerIDMessage implements Message{
    public final int id;
    public SetPlayerIDMessage(int id){
        this.id = id;
    }

    /*public TransmissionMode getTransmissionMode(){return TransmissionMode.NO_RETURN;}*/
}
