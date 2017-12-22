package engine.player;

import engine.input.KeyboardHandler;
import engine.math.Vector2f;
import engine.math.Vector2i;
import engine.physics.AABB;
import engine.render.DisplayManager;
import engine.render.sprite.Sprite;
import engine.render.texture.Texture;
import engine.util.GraphicsUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Created by anarchist on 9/3/16.
 */
public class Player extends Sprite {

    private float speed;
    private float sensitivity = 0.8f;


    public static int STATE_STANDING           = 0;
    public static int STATE_RUNNING_FORWARD    = 1;
    public static int STATE_RUNNING_RIGHT      = 2;
    public static int STATE_RUNNING_LEFT       = 3;
    public static int STATE_RUNNING_BACK       = 4;

    private int state = STATE_STANDING;

    private Vector2i runForward;
    private Vector2i runRight;
    private Vector2i runLeft;
    private Vector2i runBack;

    public Player(Texture texture) {
        super(texture);

        runForward = new Vector2i();
        runBack = new Vector2i();
        runLeft = new Vector2i();
        runRight = new Vector2i();

        setIsSolid(true);
    }

    @Override
    public void update() {
        speed = sensitivity * (float)DisplayManager.getFrameTimeSeconds();
        switch (state) {
            case 1 :
                getTransform().getPosition().y += speed;
                getSpriteAnimator().loopFrames(runForward.x,runForward.y);
                break;
            case 2:
                getTransform().getPosition().x += speed;
                getSpriteAnimator().loopFrames(runRight.x,runRight.y);
                break;
            case 3:
                getTransform().getPosition().x -= speed;
                getSpriteAnimator().loopFrames(runLeft.x,runLeft.y);
                break;
            case 4:
                getTransform().getPosition().y -= speed;
                getSpriteAnimator().loopFrames(runBack.x,runBack.y);
                break;
        }

    }

    public void setState(int state) {
        this.state = state;
    }

    public Vector2i getRunForward() {
        return runForward;
    }

    public void setRunForward(Vector2i runForward) {
        this.runForward = runForward;
    }

    public Vector2i getRunRight() {
        return runRight;
    }

    public void setRunRight(Vector2i runRight) {
        this.runRight = runRight;
    }

    public Vector2i getRunLeft() {
        return runLeft;
    }

    public void setRunLeft(Vector2i runLeft) {
        this.runLeft = runLeft;
    }

    public Vector2i getRunBack() {
        return runBack;
    }

    public void setRunBack(Vector2i runBack) {
        this.runBack = runBack;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }
}
