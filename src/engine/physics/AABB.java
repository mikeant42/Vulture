package engine.physics;

import engine.math.Vector2f;

public class AABB {
    //private Vector2f center;
    //private Vector2f halfSize;

    private Vector2f min;
    private Vector2f max;

    public AABB(Vector2f min, Vector2f max) {
        //this.center = cent;
        //this.halfSize = hSize;
        this.min = min;
        this.max = max;
    }

    public AABB() {
        this.min = new Vector2f();
        this.max = new Vector2f();
    }

    public Vector2f getMin() {
        return min;
    }

    public void setMin(Vector2f min) {
        this.min = min;
    }

    public Vector2f getMax() {
        return max;
    }

    public void setMax(Vector2f max) {
        this.max = max;
    }


    //    public boolean intersects(AABB other) {
//        if ( Math.abs(center.x - other.center.x) > halfSize.x + other.halfSize.x ) return false;
//        if ( Math.abs(center.y - other.center.y) > halfSize.y + other.halfSize.y ) return false;
//        return true;
//    }

//    public boolean intersects(AABB other) {
//        return (
//                this.max.x < other.min.x ||
//                        this.max.y < other.min.y ||
//                        this.min.x > other.max.x ||
//                        this.min.y > other.max.y
//        );
//    }

    public boolean intersects(AABB other) {
        if (this.max.x < other.min.x) {
            return false;
        }

        if (this.max.y < other.min.y) {
            return false;
        }

        if (this.min.x > other.max.x) {
            return false;
        }

        if (this.min.y > other.max.y) {
            return false;
        }

        // All tests failed, we have a intersection
        return true;
    }
}
