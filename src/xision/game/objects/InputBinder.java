package xision.game.objects;

import xision.game.GameObject;
import xision.game.XisionGame;
import xision.game.input.Binder;
import xision.game.input.Input;

/**
 * Created by Connor on 7/08/2016.
 */
public final class InputBinder extends Binder<Integer>{

    private Input input;

    public InputBinder(XisionGame game, GameObject parent, Input input){
        super(game, parent);
        this.input = input;
    }

    protected boolean isOn(Integer key){
        return input.isKeyDown(key);
    }

}
