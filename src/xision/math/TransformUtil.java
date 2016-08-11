package xision.math;

import xision.math.vector.Vec3;

/**
 * Created by Connor on 16/04/2016.
 */
public class TransformUtil{

    public static Transform perspective(float fovy, float aspect, float zNear, float zFar){

        float radians = fovy / 2.0F * 3.1415927F / 180.0F;
        float deltaZ = zFar - zNear;
        float sine = (float) Math.sin((double) radians);
        float cotangent = (float) Math.cos((double) radians) / sine;
        /*if(deltaZ != 0.0F && sine != 0.0F && aspect != 0.0F) {
            float cotangent = (float)Math.cos((double)radians) / sine;
            __gluMakeIdentityf(matrix);
            matrix.put(0, cotangent / aspect);
            matrix.put(5, cotangent);
            matrix.put(10, -(zFar + zNear) / deltaZ);
            matrix.put(11, -1.0F);
            matrix.put(14, -2.0F * zNear * zFar / deltaZ);
            matrix.put(15, 0.0F);
            GL11.glMultMatrix(matrix);
        }*/


        fovy = (float) Math.toRadians(fovy);
        float f = cotangent;
        //float deltaZ = zFar - zNear;
        return new Transform(new float[][]{
                {f / aspect, 0, 0, 0},
                {0, f, 0, 0},
                {0, 0, -(zFar + zNear) / deltaZ, -(2 * zFar * zNear) / deltaZ},
                {0, 0, -1, 0}
        });

        /*return new Transform(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, (zFar + zNear) / (zNear - zFar), (2 * zFar * zNear) / (zNear - zFar)},
                {0, 0, -1, 0}
        });*/
    }

    public static Transform orthographic(float l, float r, float b, float t, float n, float f){
        return new Transform(new float[][]{
                {2 / (r - l), 0, 0, -(r + l) / (r - l)},
                {0, 2 / (t - b), 0, -(t + b) / (t - b)},
                {0, 0, -2 / (f - n), -(f + n) / (f - n)},
                {0, 0, 0, 1},
        });
    }

    public static Transform lookAt(Vec3 cameraPos, Vec3 target){
        Vec3 F = cameraPos.sub(target);
        Vec3 f = F.normalise();
        Vec3 UP = new Vec3(0, 0, 1);
        Vec3 up = UP.normalise();
        Vec3 s = f.cross(up);
        Vec3 u = s.normalise().cross(f);
        return new Transform(new float[][]{
                {s.x, s.y, s.z, 0},
                {u.x, u.y, u.z, 0},
                {-f.x, -f.y, -f.z, 0},
                {0, 0, 0, 1}
        });
    }
}
