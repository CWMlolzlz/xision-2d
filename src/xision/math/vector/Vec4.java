package xision.math.vector;

/**
 * Created by Connor on 16/04/2016.
 */
public class Vec4 extends Vec3{

    public final float w;

    public Vec4(){
        this(0, 0, 0, 0);
    }

    /**
     * Construct a new vector, with the specified x, y, z components computes
     * and caches the magnitude.
     */
    public Vec4(float x, float y, float z, float w){
        super(x, y, z);
        this.w = w;
    }

    public float lengthSquared(){
        return super.lengthSquared() + w * w;
    }

    public Vec4 negate(){
        return new Vec4(-x, -y, -z, -w);
    }

    public Vec4 normalise(){
        float mag = length();
        return new Vec4(x / mag, y / mag, z / mag, w / mag);
    }

    public Vec4 add(Vec4 that){
        return new Vec4(this.x + that.x, this.y + that.y, this.z + that.z, this.w + that.w);
    }

    public Vec4 sub(Vec4 that){
        return add(that.negate());
    }

    public Vec4 mult(float scale){
        return new Vec4(x * scale, y * scale, z * scale, w * scale);
    }

    public Vec4 mult(Vec4 that){
        return new Vec4(this.x * that.x, this.y * that.y, this.z * that.z, this.w * that.w);
    }

    public double distance(Vec4 that){
        return sub(that).length();
    }

    public double dot(Vec4 that){
        return this.x * that.x + this.y * that.y + this.z * that.z + this.w * that.w;
    }

    public Vec4 clone(){
        return new Vec4(x, y, z, w);
    }

    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("Vect:");
        ans.append('(').append(x).append(',').append(y).append(',').append(z).append(',').append(w)
                .append(')');
        return ans.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Vec4)) return false;
        if(!super.equals(o)) return false;

        Vec4 vec4 = (Vec4) o;

        return Float.compare(vec4.w, w) == 0;

    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
        return result;
    }
}
