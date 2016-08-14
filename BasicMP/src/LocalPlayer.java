import xision.game.GameObject;
import xision.game.XisionGame;
import xision.game.input.Key;
import xision.game.input.KeyBinder;
import xision.math.vector.Vec2;

import java.awt.*;

/**
 * Created by Connor on 11/08/2016.
 */
public class LocalPlayer extends Player{

    KeyBinder inputBinder;

    public LocalPlayer(XisionGame game, GameObject parent, int id){
        super(game,parent, id);
        inputBinder = new KeyBinder(game,this,game.input);
        inputBinder.bind(Key.KEY_W, () -> move(new Vec2(0,-1)));
        inputBinder.bind(Key.KEY_S, () -> move(new Vec2(0,1)));
        inputBinder.bind(Key.KEY_A, () -> move(new Vec2(-1,0)));
        inputBinder.bind(Key.KEY_D, () -> move(new Vec2(1,0)));
    }


    @Override
    protected void draw(Graphics2D g){
        g.drawRect(0,0,100,100);
        g.drawString("ID: " + this.getID(), 0,0);
    }

    @Override
    public void update(float delta){

    }
}
