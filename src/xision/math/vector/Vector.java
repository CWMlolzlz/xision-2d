package xision.math.vector;

import xision.math.vector.Vec2;

/**
 * Created by Connor on 5/07/2016.
 */
public interface Vector<T extends Vec2>{

    double lengthSquared();

    T negate();

    T normalise();

    T add(T that);

    T sub(T that);

    T mult(double scale);

    T mult(T that);

    double distance(T that);

    double dot(T that);

    T clone();

}
