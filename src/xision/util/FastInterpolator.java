package xision.util;

/**
 * Created by Connor on 15/04/2016.
 */
public class FastInterpolator{
    float a, b, c, d;
    float k;

    public FastInterpolator(float inLo, float inHi, float outLo, float outHi){
        a = inLo;
        b = inHi;
        c = outLo;
        d = outHi;
        k = (outHi - outLo) / (inHi - inLo);
    }

    public float interpolate(float in){
        if(a == b) return 0;
        return ((in - a) * k) + c;
    }

}
