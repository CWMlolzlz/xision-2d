import xision.game.GameObject;
import xision.game.XisionGame;

/**
 * Created by Connor on 13/08/2016.
 */
public abstract class Actor extends GameObject{

    private int id;

    public Actor(XisionGame game, GameObject parent, int id){
        super(game,parent);
        this.id = id;
    }

    public final int getID(){
        return id;
    }

}
