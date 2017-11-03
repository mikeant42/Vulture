package engine.physics;

import engine.math.Vector2f;

public class AABB {
    private Vector2f center;
    private Vector2f halfSize;

    public AABB(Vector2f cent, Vector2f hSize) {
        this.center = cent;
        this.halfSize = hSize;
    }

    public boolean intersects(AABB other) {
        if ( Math.abs(center.x - other.center.x) > halfSize.x + other.halfSize.x ) return false;
        if ( Math.abs(center.y - other.center.y) > halfSize.y + other.halfSize.y ) return false;
        return true;
    }
}
