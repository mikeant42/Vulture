package engine.render;

import engine.base.Node;
import engine.input.KeyboardHandler;
import engine.math.Matrix4f;
import engine.math.Vector;
import engine.math.Vector2f;
import engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import engine.util.MathUtil;

/**
 * Created by anarchist on 8/30/16.
 */
public class Camera {
    private Vector2f position;

    private float pitch = 0;
    private float yaw = 0;

    private Node player;

    public Camera() {
        position = new Vector2f(0, 0);
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
            //position = player.getTransform().getPosition();

            // Smooth the camera using the "Michael Smooth" method
            float posX = MathUtil.Lerp(position.x, this.player.getTransform().getPosition().x, 0.045f);
            float posY = MathUtil.Lerp(position.y, this.player.getTransform().getPosition().y, 0.045f);
            position.x = posX;
            position.y = posY;
        }
    }

    public Vector2f screenToWorld(Vector2f mouseCoords) {
        return new Vector2f((position.x + (mouseCoords.x/DisplayManager.getWindowWidth())) - position.x
                , (position.y + (mouseCoords.y/DisplayManager.getWindowHeight())) - position.y);
    }
}
