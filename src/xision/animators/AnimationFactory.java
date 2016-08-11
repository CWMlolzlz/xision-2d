package xision.animators;

import xision.math.vector.Vec2;

/**
 * Created by Connor on 12/07/2016.
 */
public class AnimationFactory{

    /* STATIC ANIMATION */
    public static Animation createStaticAnimation(){
        return createStaticAnimation(0, 0);
    }

    public static Animation createStaticAnimation(float x, float y){
        return createStaticAnimation(new Vec2(x, y));
    }

    public static Animation createStaticAnimation(Vec2 v){
        return new StaticAnimation(v);
    }


    /* LOOPING ANIMATION */
    public static Animation createLoopingAnimation(float[] x, float[] y){
        return new LoopingAnimation(x, y);
    }


    /* JITTER ANIMATION */
    public static Animation createJitterAnimator(float dx, float dy){
        return createJitterAnimator(new Vec2(dx, dy));
    }

    public static Animation createJitterAnimator(Vec2 dv){
        return new JitterAnimation(dv);
    }

    /* ANIMATION SET */


}
