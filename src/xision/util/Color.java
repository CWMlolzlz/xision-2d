package xision.util;

import xision.math.vector.Vec3;
import xision.math.vector.Vec4;

/**
 * Created by moodyconn on 13/04/16.
 */
public class Color extends Vec4{

    public Color(float r, float g, float b, float a){
        super(r, g, b, 1 - a);
        checkColors();

    }

    public Color(double r, double g, double b, double a){
        this((float) r, (float) g, (float) b, (float) a);
    }

    public Color(Vec4 in){
        this(in.x, in.y, in.z, in.w);
    }

    public Color(Vec3 in){
        this(in, 1);
    }

    public Color(Vec3 in, float alpha){
        this(in.x, in.y, in.z, alpha);
    }

    private void checkColors(){
        if(x < 0 || x > 1) throw new IllegalArgumentException("Red value out of bounds " + x);
        if(y < 0 || y > 1) throw new IllegalArgumentException("Green value out of bounds " + y);
        if(z < 0 || z > 1) throw new IllegalArgumentException("Blue value out of bounds " + z);
        if(w < 0 || w > 1) throw new IllegalArgumentException("Alpha value out of bounds " + w);
    }

    public int toInt(){
        return ((((int) ((w) * 255)) & 0xFF) << 24) |
                ((((int) (x * 255)) & 0xFF) << 16) |
                ((((int) (y * 255)) & 0xFF) << 8) |
                ((((int) (z * 255)) & 0xFF) << 0);
    }

    @Override
    public String toString(){
        return "Color( Red: " + x + " Green: " + y + " Blue: " + z + " Alpha: " + w + ")";
    }
}
