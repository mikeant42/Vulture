package engine.render;

import engine.base.Node;
import engine.input.KeyboardHandler;
import engine.math.Vector2f;
import org.lwjgl.glfw.GLFW;

/**
 * Created by anarchist on 8/30/16.
 */
public class Camera {
    private Vector2f position;

    private float pitch = 0;
    private float yaw = 0;

    private Node player;

    public Camera() {
        position = new Vector2f(0,0);
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPlayer(Node player) {
        this.player = player;
    }

    public void update() {
        if (player != null) {
            position = player.getTransform().getPosition();
        }
    }


}
