import xision.game.XisionGame;

import java.util.HashMap;

/**
 * Created by Connor on 13/08/2016.
 */
public class ActorManager{

    private HashMap<Integer, Actor> actors = new HashMap<>();

    public void addActor(Actor actor){
        actors.put(actor.getID(),actor);
    }

    /*public Actor getActor(int id){
        return actors.get(id);
    }*/

    @SuppressWarnings("unchecked")
    public <T extends Actor> T getActor(int id){
        Actor actor = actors.get(id);
        try{
            return (T) actor;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        return null;
    }

}
