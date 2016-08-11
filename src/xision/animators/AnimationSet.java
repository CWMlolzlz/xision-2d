package xision.animators;

import xision.math.vector.Vec2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Connor on 13/07/2016.
 */
public class AnimationSet implements Animation{

    private final Set<Animation> animations = new HashSet<>();

    public AnimationSet(Animation... anims){
        this(Arrays.asList(anims));
    }

    public AnimationSet(Collection<? extends Animation> anims){
        animations.addAll(anims);
    }

    public Vec2 next(){
        return null;
    }

}
