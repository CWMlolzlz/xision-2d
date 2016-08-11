package xision.communication.messages;

import xision.communication.Message;

/**
 * Created by Connor on 9/08/2016.
 */
public class StringMessage extends Message{

    private String string;

    public StringMessage(String s){
        this.string = s;
    }

    public TransmissionType getTransmissionMode(){return TransmissionType.ECHO;}

    public String getString(){return string;}

    public String toString(){
        return this.string;
    }

}
