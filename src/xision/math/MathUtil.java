package xision.math;

/**
 * Created by Connor on 16/04/2016.
 */
public class MathUtil{

    public static float cos(double a){
        return (float) Math.cos(a);
    }

    public static float sin(double a){
        return (float) Math.sin(a);
    }

    public static float tan(double a){
        return (float) Math.tan(a);
    }

    public static float abs(double a){
        return (float) Math.abs(a);
    }

    public static float random(){
        return (float) Math.random();
    }

    public static double clamp(double lo, double hi, double val){
        return Math.min(hi, Math.max(lo, val));
    }

    public static float clamp(float lo, float hi, float val){
        return Math.min(hi, Math.max(lo, val));
    }

    public static float saturate(float val){
        return clamp(0, 1, val);
    }

    public static float pow(float a, float b){
        return (float) Math.pow(a, b);
    }

    public static float lint(float in, float inlo, float inhi, float outlo, float outhi){
        if(inlo == inhi) return 0;
        return (((in - inlo) / (inhi - inlo)) * (outhi - outlo)) + outlo;
    }

    public static float mult(float a, float b){
        return a * b;
    }

    public static float max(float a, float b){
        return Math.max(a, b);
    }

    public static float max(float... v){
        float val = v[0];
        for(int i = 1; i < v.length; i++){
            val = max(val, v[i]);
        }
        return val;
    }

    public static float min(float... v){
        float val = v[0];
        for(int i = 1; i < v.length; i++){
            val = min(val, v[i]);
        }
        return val;
    }

    public static float min(float a, float b){
        return Math.min(a, b);
    }
}
