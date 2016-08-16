package messages;

import xision.communication.Message;

/**
 * Created by Connor on 11/08/2016.
 */
public class ActorMoveMessage implements Message{

    public final int actorID;
    public final float x,y;

    public ActorMoveMessage(int actorID, float x, float y){
        this.actorID = actorID;
        this.x = x;
        this.y = y;
    }

    /*public TransmissionMode getTransmissionMode(){
        return TransmissionMode.NO_RETURN_BROADCAST;
    }*/

}
