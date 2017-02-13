package engine.player;

import engine.input.KeyboardHandler;
import engine.render.DisplayManager;
import engine.render.ship.StarshipSprite;
import engine.render.sprite.Sprite;
import engine.render.texture.Texture;
import org.lwjgl.glfw.GLFW;

/**
 * Created by anarchist on 2/12/2017.
 */
public class Starship extends StarshipSprite {

    private float thrust = 0.01f;
    private float decay = .97f;
    private float maxSpeed = 3;

    private float turningSpeed = 2;

    private float speed;

    private float xSpeed;
    private float ySpeed;

    private float maxRotate = 5;

    private float rotate;

    public Starship(Texture texture) {
        //super(texture);
        super();
    }

    @Override
    public void update() {

        // Keep speed limit
        speed = (float)Math.sqrt((xSpeed*xSpeed)+(ySpeed*ySpeed));
        if (speed > maxSpeed) {
            xSpeed *= maxSpeed/speed;
            ySpeed *= maxSpeed/speed;
        }

        getTransform().getPosition().y += (float)DisplayManager.getFrameTimeSeconds() * ySpeed;
        getTransform().getPosition().x += (float)DisplayManager.getFrameTimeSeconds() * xSpeed;

        getTransform().getRotation().x -= (float)DisplayManager.getFrameTimeSeconds() *  rotate;

        // The rotate needs to die down over time
        //rotate *= 0.000001;
    }

    @Override
    public void input() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            if (rotate < maxRotate)
                rotate += turningSpeed;
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            rotate -= turningSpeed;
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            xSpeed += thrust*Math.sin(rotate*(Math.PI/180));
            ySpeed += thrust*Math.cos(rotate*(Math.PI/180));
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            xSpeed *= decay;
            ySpeed *= decay;
        }

    }
}
