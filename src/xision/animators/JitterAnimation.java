package xision.animators;

import xision.math.vector.Vec2;

/**
 * Created by Connor on 12/07/2016.
 */
public class JitterAnimation implements Animation{

    private Vec2 maxV;

    JitterAnimation(Vec2 v){
        maxV = v.clone();
    }

    public Vec2 next(){
        float mx = (float) Math.random() * 2 - 1;
        float my = (float) Math.random() * 2 - 1;
        return new Vec2(maxV.x * mx, maxV.y * my);
    }

}
