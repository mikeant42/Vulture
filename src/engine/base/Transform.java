package engine.base;


import engine.math.Vector2f;

/**
 * Created by anarchist on 8/28/16.
 */
public class Transform {
    private Vector2f position;
    private Vector2f rotation;
    private float scale;

    public Transform(Vector2f position, Vector2f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform() {
        this(new Vector2f(0,0), new Vector2f(0,0), 1);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
