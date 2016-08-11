package xision.math;

import xision.math.vector.Vec3;

/**
 * 3x4 array representing an affine transformation (= a 4x4 martrix in which the
 * bottom row is always {0 0 0 1} ) Note that this cannot be used for
 * perspective projection tranformations since these require a non-0 bottom row.
 * <p>
 * The class provides static methods to construct translation, scaling, and
 * rotation matrices, and methods to multiply a translation by a vector or
 * another matrix.
 *
 * @author Pondy
 */
public class Transform{

    private final float[][] values;

    /**
     * Construct a Transformation given 3x4 array of elements
     */
    public Transform(float[][] v){
        if(v.length != 4 || v[0].length != 4)
            throw new IllegalArgumentException(
                    "Transform: Wrong size array for argument: " + v);
        else
            values = v;
    }

    /**
     * Construct an identity Transformation
     */
    public static Transform identity(){
        return new Transform(new float[][]{
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /**
     * Construct a translation Transformation based on a vector
     */
    public static Transform newTranslation(Vec3 tr){
        return newTranslation(tr.x, tr.y, tr.z);
    }

    /**
     * Construct a translation Transformation given dx, dy, dz
     */
    public static Transform newTranslation(float tx, float ty, float tz){
        return new Transform(new float[][]{{1.0f, 0.0f, 0.0f, tx},
                {0.0f, 1.0f, 0.0f, ty},
                {0.0f, 0.0f, 1.0f, tz},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /**
     * Construct a scaling Transformation given values in a vector
     */
    public static Transform newScale(Vec3 sc){
        return newScale(sc.x, sc.y, sc.z);
    }

    /**
     * Construct a scaling Transformation given sx, sy, sz
     */
    public static Transform newScale(float sx, float sy, float sz){
        return new Transform(new float[][]{
                {sx, 0.0f, 0.0f, 0.0f},
                {0.0f, sy, 0.0f, 0.0f},
                {0.0f, 0.0f, sz, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}

        });
    }

    /**
     * Construct a rotation Transformation given angle around x axis
     */
    public static Transform newXRotation(float th){
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Transform(new float[][]{
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, costh, -sinth, 0.0f},
                {0.0f, sinth, costh, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /**
     * Construct a rotation Transformation given angle around y axis
     */
    public static Transform newYRotation(float th){
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Transform(new float[][]{
                {costh, 0.0f, sinth, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {-sinth, 0.0f, costh, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /**
     * Construct a rotation Transformation given angle around z axis
     */
    public static Transform newZRotation(float th){
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Transform(new float[][]{
                {costh, -sinth, 0.0f, 0.0f},
                {sinth, costh, 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /* post multiply this transform by another (this * other) */
    public Transform compose(Transform other){
        float[][] ans = new float[4][4];
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col++){
                for(int i = 0; i < 3; i++){
                    ans[row][col] += this.values[row][i] * other.values[i][col];
                }
            }
            ans[row][3] += this.values[row][3];
        }
        return new Transform(ans);
    }


    /* apply this transform to a vector */
    public Vec3 multiply(Vec3 vect){
        if(values == null || values[0] == null || values[1] == null
                || values[2] == null){
            throw new IllegalStateException("Ill-formed transform");
        }
        if(vect == null){
            throw new IllegalArgumentException("multiply by null vector");
        }
        float x = values[0][3];
        float y = values[1][3];
        float z = values[2][3];
        x += values[0][0] * vect.x + values[0][1] * vect.y + values[0][2]
                * vect.z;
        y += values[1][0] * vect.x + values[1][1] * vect.y + values[1][2]
                * vect.z;
        z += values[2][0] * vect.x + values[2][1] * vect.y + values[2][2]
                * vect.z;
        return new Vec3(x, y, z);
    }

    public Transform transpose(){
        return new Transform(new float[][]{
                {values[0][0], values[1][0], values[2][0], values[3][0]},
                {values[0][1], values[1][1], values[2][1], values[3][1]},
                {values[0][2], values[1][2], values[2][2], values[3][2]},
                {values[0][3], values[1][3], values[2][3], values[3][3]}
        });
    }

    public Transform inverse(){
        float det = det();
        if(det == 0) return null;
        float a11, a12, a13, a14;
        float a21, a22, a23, a24;
        float a31, a32, a33, a34;
        float a41, a42, a43, a44;

        float b11, b12, b13;//,b14;
        float b21, b22, b23;//,b24;
        float b31, b32, b33;//,b34;
        //float b41,b42,b43,b44;

        a11 = values[0][0];
        a12 = values[0][1];
        a13 = values[0][2];
        a14 = values[0][3];

        a21 = values[1][0];
        a22 = values[1][1];
        a23 = values[1][2];
        a24 = values[1][3];

        a31 = values[2][0];
        a32 = values[2][1];
        a33 = values[2][2];
        a34 = values[2][3];

        a41 = values[3][0];
        a42 = values[3][1];
        a43 = values[3][2];
        a44 = values[3][3];

        b11 = a22 * a33 - a23 * a32;
        b12 = a13 * a32 - a12 * a33;
        b13 = a12 * a23 - a13 * a22;

        b21 = a23 * a31 - a21 * a33;
        b22 = a11 * a33 - a13 * a31;
        b23 = a13 * a21 - a11 * a23;

        b31 = a21 * a32 - a22 * a31;
        b32 = a12 * a31 - a11 * a32;
        b33 = a11 * a22 - a12 * a21;




		/*b11 = a22*a33*a44 + a23*a34*a42 + a24*a32*a43 - a22*a34*a43 - a23*a32*a44 - a24*a33*a42;
        b12 = a12*a34*a43 + a13*a32*a44 + a14*a33*a42 - a12*a33*a44 - a13*a34*a42 - a13*a32*a43;
		b13 = a12*a23*a44 + a13*a24*a42 + a14*a22*a43 - a12*a24*a43 - a13*a22*a44 - a14*a23*a42;
		b14 = a12*a24*a33 + a13*a22*a34 + a14*a23*a32 - a12*a23*a34 - a13*a24*a32 - a14*a22*a33;

		b21 = a21*a34*a43 + a23*a31*a44 + a24*a33*a41 - a21*a33*a44 - a23*a34*a41 - a24*a31*a43;
		b22 = a11*a33*a44 + a13*a34*a41 + a14*a31*a43 - a11*a34*a43 - a13*a31*a44 - a14*a33*a41;
		b23 = a11*a24*a43 + a13*a21*a44 + a14*a23*a41 - a11*a23*a44 - a13*a24*a41 - a14*a21*a43;
		b24 = a11*a23*a34 + a13*a24*a31 + a14*a21*a33 - a11*a24*a44 - a13*a21*a34 - a14*a23*a31;

		b31 = a21*a32*a44 + a22*a34*a41 + a24*a31*a42 - a21*a34*a42 - a22*a31*a44 - a24*a32*a41;
		b32 = a11*a34*a42 + a12*a31*a44 + a14*a32*a41 - a11*a32*a44 - a12*a34*a41 - a14*a31*a42;
		b33 = a11*a22*a44 + a12*a24*a41 + a14*a21*a42 - a11*a24*a42 - a12*a21*a44 - a14*a22*a44;
		b34 = a11*a24*a32 + a12*a21*a34 + a14*a22*a31 - a11*a22*a34 - a12*a24*a31 - a14*a21*a32;

		b41 = a21*a33*a42 + a22*a31*a43 + a23*a32*a41 - a21*a32*a43 - a22*a33*a41 - a23*a31*a42;
		b42 = a11*a32*a43 + a12*a33*a41 + a13*a31*a42 - a11*a33*a42 - a12*a31*a43 - a13*a32*a41;
		b43 = a11*a23*a42 + a12*a21*a43 + a13*a22*a41 - a11*a22*a43 - a12*a23*a41 - a13*a21*a42;
		b44 = a11*a22*a33 + a12*a23*a31 + a13*a21*a32 - a11*a23*a32 - a12*a21*a33 - a13*a22*a31;*/

        return new Transform(new float[][]{
                {b11, b12, b13, 0},
                {b21, b22, b23, 0},
                {b31, b32, b33, 0},
                {0, 0, 0, 1}
        }).mult(1 / det);


    }

    public Transform mult(float f){
        float a11, a12, a13, a14;
        float a21, a22, a23, a24;
        float a31, a32, a33, a34;
        float a41, a42, a43, a44;

        a11 = values[0][0];
        a12 = values[0][1];
        a13 = values[0][2];
        a14 = values[0][3];

        a21 = values[1][0];
        a22 = values[1][1];
        a23 = values[1][2];
        a24 = values[1][3];

        a31 = values[2][0];
        a32 = values[2][1];
        a33 = values[2][2];
        a34 = values[2][3];

        a41 = values[3][0];
        a42 = values[3][1];
        a43 = values[3][2];
        a44 = values[3][3];
        return new Transform(new float[][]{
                {a11 * f, a12 * f, a13 * f, a14 * f},
                {a21 * f, a22 * f, a23 * f, a24 * f},
                {a31 * f, a32 * f, a33 * f, a34 * f},
                {a41 * f, a42 * f, a43 * f, a44 * f}
        });
    }

    public float det(){
        float a11, a12, a13, a14;
        float a21, a22, a23, a24;
        float a31, a32, a33, a34;
        float a41, a42, a43, a44;

        a11 = values[0][0];
        a12 = values[0][1];
        a13 = values[0][2];
        a14 = values[0][3];

        a21 = values[1][0];
        a22 = values[1][1];
        a23 = values[1][2];
        a24 = values[1][3];

        a31 = values[2][0];
        a32 = values[2][1];
        a33 = values[2][2];
        a34 = values[2][3];

        a41 = values[3][0];
        a42 = values[3][1];
        a43 = values[3][2];
        a44 = values[3][3];

        float det = a11 * a22 * a33 * a44 + a11 * a23 * a34 * a42 + a11 * a24 * a32 * a43
                + a12 * a21 * a34 * a43 + a12 * a23 * a31 * a44 + a12 * a24 * a33 * a41
                + a13 * a21 * a32 * a44 + a13 * a22 * a32 * a41 + a13 * a24 * a31 * a42
                + a14 * a21 * a33 * a42 + a14 * a22 * a31 * a43 + a14 * a23 * a32 * a41

                - a11 * a22 * a34 * a43 - a11 * a23 * a32 * a44 - a11 * a24 * a33 * a42
                - a12 * a21 * a33 * a44 - a12 * a23 * a34 * a41 - a12 * a24 * a31 * a43
                - a13 * a21 * a34 * a42 - a13 * a22 * a31 * a44 - a13 * a24 * a32 * a41
                - a14 * a21 * a32 * a43 - a14 * a22 * a33 * a41 - a14 * a23 * a31 * a42;
        return det;
    }

    public String toString(){
        StringBuilder ans = new StringBuilder();
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col++){
                ans.append(values[row][col]).append(' ');
            }
            ans.append('\n');
        }
        return ans.toString();
    }

}

// code for COMP261 assignments
