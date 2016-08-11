package xision.animators;

import xision.math.vector.Vec2;

/**
 * Created by Connor on 12/07/2016.
 */
public class StaticAnimation implements Animation{

    private final Vec2 vector;

    StaticAnimation(Vec2 v){
        vector = v.clone();
    }

    public Vec2 next(){
        return vector;
    }
}
