package xision.math.vector;

/**
 * An immutable 3D vector or position. Note that it is safe to make the fields
 * public because they are final and cannot be modified.
 *
 * @author Pondy
 */
public class Vec3 extends Vec2{

    public static final Vec3 ZERO = new Vec3(0, 0, 0);

    public final float z;

    public Vec3(){
        this(0, 0, 0);
    }

    /**
     * Construct a new vector, with the specified x, y, z components computes
     * and caches the magnitude.
     */
    public Vec3(float x, float y, float z){
        super(x, y);
        this.z = z;
    }

    public float lengthSquared(){
        return super.lengthSquared() + z * z;
    }

    public Vec3 negate(){
        return new Vec3(-x, -y, -z);
    }

    public Vec3 normalise(){
        float mag = length();
        return new Vec3(x / mag, y / mag, z / mag);
    }

    public Vec3 add(Vec3 that){
        return new Vec3(this.x + that.x, this.y + that.y, this.z + that.z);
    }

    public Vec3 sub(Vec3 that){
        return add(that.negate());
    }

    public Vec3 mult(float scale){
        return new Vec3(x * scale, y * scale, z * scale);
    }

    public Vec3 mult(Vec3 that){
        return new Vec3(this.x * that.x, this.y * that.y, this.z * that.z);
    }

    public double distance(Vec3 that){
        return sub(that).length();
    }

    public double dot(Vec3 that){
        return this.x * that.x + this.y * that.y + this.z * that.z;
    }

    public Vec3 cross(Vec3 that){
        if(this.lengthSquared() == 0 || that.lengthSquared() == 0) return new Vec3(0, 0, 0);
        return new Vec3(this.y * that.z - this.z * that.y,
                this.z * that.x - this.x * that.z,
                this.x * that.y - this.y * that.x);
    }

    public Vec3 clone(){
        return new Vec3(x, y, z);
    }

    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("Vect:");
        ans.append('(').append(x).append(',').append(y).append(',').append(z)
                .append(')');
        return ans.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Vec3)) return false;
        if(!super.equals(o)) return false;

        Vec3 vec3 = (Vec3) o;

        return Float.compare(vec3.z, z) == 0;

    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }
}

// code for COMP261 assignments
