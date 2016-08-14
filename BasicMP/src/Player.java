import xision.game.GameObject;
import xision.game.XisionGame;

import java.awt.*;

/**
 * Created by Connor on 11/08/2016.
 */
public class Player extends Actor{



    public Player(XisionGame game, GameObject parent, int id){
        super(game,parent, id);


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
