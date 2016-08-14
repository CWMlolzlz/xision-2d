package messages;

import xision.communication.Message;

/**
 * Created by Connor on 11/08/2016.
 */
public class NewPlayerMessage extends Message{
    public final int id;
    public NewPlayerMessage(int id){
        this.id = id;
    }

    /*public TransmissionMode getTransmissionMode(){return TransmissionMode.NO_RETURN;}*/
}
