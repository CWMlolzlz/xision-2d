package xision.game.input;

import xision.game.GameObject;
import xision.game.XisionGame;
import xision.events.PolledBinder;

/**
 * Created by Connor on 7/08/2016.
 */
public final class KeyBinder extends PolledBinder<Key>{

    private Input input;

    public KeyBinder(XisionGame game, GameObject parent){
        this(game,parent,game.input);
    }

    public KeyBinder(XisionGame game, GameObject parent, Input input){
        super(game, parent);
        this.input = input;
    }

    protected boolean isOn(Key key){
        return input.isKeyDown(key);
    }

}
