package engine.player;

import engine.input.KeyboardHandler;
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
    private float sensitivity = 1.2f;
    private AABB aabb;
    private int currentIndex = 1;

    public Player(Texture texture) {
        super(texture);
    }
    @Override
    public void update() {
        speed = sensitivity * (float)DisplayManager.getFrameTimeSeconds();
    }

    @Override
    public void input() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            getTransform().getPosition().x += speed;
            getSpriteAnimator().loopFrames(9,12);
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            getTransform().getPosition().x -= speed;
            getSpriteAnimator().loopFrames(5,8);
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            getTransform().getPosition().y += speed;
            getSpriteAnimator().loopFrames(13,16);
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            getTransform().getPosition().y -= speed;
            getSpriteAnimator().loopFrames(1,4);
        }


    }
}
