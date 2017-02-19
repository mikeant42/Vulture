package engine.terrain;

import engine.math.Vector2f;

/**
 * Created by anarchist on 2/18/2017.
 */
public class MetaBall {

    public final Vector2f position = new Vector2f(0,0);
    public final float radiusSquared;
    public final float radius;
    public final Vector2f velocity = new Vector2f(0,0);

    public MetaBall(Vector2f position,float radius) {
        this.position.set(position);
        this.radius = radius;
        this.radiusSquared = radius*radius;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getRadiusSquared() {
        return radiusSquared;
    }

    public float getRadius() {
        return radius;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public float dst2(float x, float y)
    {
        final float dx = position.x - x;
        final float dy = position.y - y;
        return dx*dx+dy*dy;
    }

    public void move(Vector2f min,Vector2f max,float deltaSeconds) {

        float dx = deltaSeconds*velocity.x;
        float dy = deltaSeconds*velocity.y;

        float newX = position.x + dx;
        float newY = position.y + dy;

        if ( (newX-radius) < min.x ) {
            newX = position.x - dx;
            velocity.x = -velocity.x;
        } else if ( (newX+radius) > max.x ) {
            newX = position.x - dx;
            velocity.x = -velocity.x;
        }


        if ( (newY-radius) < min.y ) {
            newY = position.y - dy;
            velocity.y = -velocity.y;
        } else if ( (newY+radius) > max.y ) {
            newY = position.y - dy;
            velocity.y = -velocity.y;
        }
        position.x = newX;
        position.y = newY;
    }
}
