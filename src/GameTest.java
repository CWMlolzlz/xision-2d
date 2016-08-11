import xision.game.GameObject;
import xision.game.XisionGame;
import xision.game.input.Key;
import xision.game.objects.InputBinder;
import xision.math.vector.Vec2;

import java.awt.*;

/**
 * Created by Connor on 6/08/2016.
 */
public class GameTest{

    public static void main(String[] args){
        XisionGame game = XisionGame.create(300, 300);
        Player player = new Player(game, null);

        GameObject a = new GameObject(game,player){

            {this.position = new Vec2(2.5f,2.5f);}

            @Override
            protected void draw(Graphics2D g){
                g.drawRect(0,0,5,5);
            }

            @Override
            public void update(float delta){}
        };

        game.setDebugLayerVisible(true);
    }

    private static class Player extends GameObject{

        private final InputBinder inputBinder;

        public Player(XisionGame game, GameObject parent){
            super(game, parent);

            this.size = new Vec2(100,100);

            inputBinder = new InputBinder(game, this, game.input);

            inputBinder.bind(Key.KEY_UP, () -> this.position = this.position.add(new Vec2(0,-1)));
            inputBinder.bind(Key.KEY_DOWN, () -> this.position = this.position.add(new Vec2(0,1)));
            inputBinder.bind(Key.KEY_LEFT, () -> this.position = this.position.add(new Vec2(-1,0)));
            inputBinder.bind(Key.KEY_RIGHT, () -> this.position = this.position.add(new Vec2(1,0)));
            inputBinder.bind(Key.KEY_SPACE, () -> this.rotation += 0.03f);
            //inputBinder.unbind();

        }

        @Override
        public void draw(Graphics2D g){
            g.drawRect(0,0,(int)size.x,(int)size.y);
        }

        @Override
        public void update(float delta){}
    }

}
