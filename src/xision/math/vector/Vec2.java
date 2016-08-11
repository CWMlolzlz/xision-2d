package xision.math.vector;

/**
 * Created by Connor on 16/04/2016.
 */
public class Vec2 implements Cloneable{

    public static final Vec2 ZERO = new Vec2(0, 0);

    public final float x, y;

    public Vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float lengthSquared(){
        return x * x + y * y;
    }

    public final float length(){
        return (float) Math.sqrt(lengthSquared());
    }

    public Vec2 negate(){
        return new Vec2(-x, -y);
    }

    public Vec2 normalise(){
        float mag = length();
        return new Vec2(x / mag, y / mag);
    }

    public Vec2 add(Vec2 that){
        return new Vec2(this.x + that.x, this.y + that.y);
    }

    public Vec2 sub(Vec2 that){
        return add(that.negate());
    }

    public Vec2 mult(float scale){
        return new Vec2(x * scale, y * scale);
    }

    public Vec2 mult(Vec2 that){
        return new Vec2(this.x * that.x, this.y * that.y);
    }

    public double distance(Vec2 that){
        return sub(that).length();
    }

    public double dot(Vec2 that){
        return this.x * that.x + this.y * that.y;
    }

    public Vec2 clone(){
        return new Vec2(x, y);
    }

    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("");
        ans.append('(').append(x).append(',').append(y).append(')');
        return ans.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Vec2)) return false;

        Vec2 vec2 = (Vec2)o;

        return Float.compare(vec2.x, x) == 0 && Float.compare(vec2.y, y) == 0;

    }

    @Override
    public int hashCode(){
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }
}
