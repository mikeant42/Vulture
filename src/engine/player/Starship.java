package engine.player;

import engine.input.KeyboardHandler;
import engine.math.Vector2f;
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

    private float speed = 0;

    private float xSpeed;
    private float ySpeed;

    private float maxRotate = 5;

    private float rotate;

    private float angle = 1;
    private boolean driving = false;
    private float accel = 0;
    private Vector2f direction = new Vector2f();

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

//        getTransform().getPosition().y += (float)DisplayManager.getFrameTimeSeconds() * ySpeed;
//        getTransform().getPosition().x += (float)DisplayManager.getFrameTimeSeconds() * xSpeed;
//
//        getTransform().getRotation().x -= (float)DisplayManager.getFrameTimeSeconds() *  rotate;

        // The rotate needs to die down over time
        //rotate *= 0.000001;

        if (accel < 0) {
            accel = 0;
        }

        getTransform().getPosition().x += (direction.x * accel) * (float)DisplayManager.getFrameTimeSeconds();
        getTransform().getPosition().y += (direction.y * accel) * (float)DisplayManager.getFrameTimeSeconds();
        float newAngle = angle * (float)DisplayManager.getFrameTimeSeconds();
        getTransform().getRotation().set(newAngle, newAngle);
    }

    @Override
    public void input() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            angle -= 50;
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            angle += 50;
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            if (!driving) {
                driving = true;
                accel += 0.2f;
            }

            direction = new Vector2f((float)Math.cos(angle), (float)Math.sin(angle));

            accel += 0.02f;
            if (accel > 0.5f) {
                accel = 0.5f;
            }
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            driving = false;
            accel -= 0.06f;
        }

    }
}
