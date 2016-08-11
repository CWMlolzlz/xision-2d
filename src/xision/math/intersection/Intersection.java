package xision.math.intersection;

import xision.math.vector.Vec2;

/**
 * Created by Connor on 5/07/2016.
 */
public class Intersection{
    public static Vec2 lineToLineSegment(Vec2 a1, Vec2 a2, Vec2 b1, Vec2 b2){
        double denom = (a1.x - a2.x) * (b1.y - b2.y) - (a1.y - a2.y) * (b1.x - b2.x);
        if(denom != 0){
            float outX = (float) (((a1.x * a2.y - a1.y * a2.x) *
                    (b1.x - b2.x) - (a1.x - a2.x) *
                    (b1.x * b2.y - b1.y * b2.x)) / denom);
            float outY = (float) (((a1.x * a2.y - a1.y * a2.x) *
                    (b1.y - b2.y) - (a1.y - a2.y) *
                    (b1.x * b2.y - b1.y * b2.x)) / denom);

            double maxX = Math.max(b1.x, b2.x);
            double minX = Math.min(b1.x, b2.x);
            double maxY = Math.max(b1.y, b2.y);
            double minY = Math.min(b1.y, b2.y);

            if((outX <= maxX && outX >= minX) && (outY <= maxY && outY >= minY)){
                return new Vec2(outX, outY);
            }
        }
        return null;
    }

    public static Vec2 lineSegmentToLineSegment(Vec2 a1, Vec2 a2, Vec2 b1, Vec2 b2){
        Vec2 result = Intersection.lineToLineSegment(a1, a2, b1, b2);

        if(result != null){
            if((result.x >= Math.min(a1.x, a2.x) &&
                    result.x <= Math.max(a1.x, a2.x) &&
                    result.y >= Math.min(a1.y, a2.y) &&
                    result.y <= Math.max(a1.y, a2.y))){
                return result;
            }
        }

        return null;
    }

    public static boolean pointOnLine(Vec2 p, Vec2 a, Vec2 b){
        return a.distance(p) + b.distance(p) == a.distance(b);
    }
}
