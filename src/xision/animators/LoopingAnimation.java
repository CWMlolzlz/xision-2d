package xision.animators;

import xision.math.vector.Vec2;

/**
 * Created by Connor on 12/07/2016.
 */
public class LoopingAnimation implements Animation{

    private final Vec2[] frames;
    int frame;

    LoopingAnimation(float[] x, float[] y){
        if(x.length == 0 || y.length == 0)
            throw new IllegalArgumentException("X and Y position arrays may not be empty");
        if(x.length != y.length)
            throw new IllegalArgumentException("X and Y position arrays do not have matching length");

        frames = new Vec2[x.length];

        for(int i = 0; i < frames.length; i++){
            frames[i] = new Vec2(x[i], y[i]);
        }
    }

    public Vec2 next(){
        if(frame >= frames.length)
            frame = 0;
        return frames[frame++];
    }

}
