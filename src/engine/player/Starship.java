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

    private boolean driving = false;
    private float thrust = 0.001f;
    private float turnSpeed = 10;
    private float angle = 0;
    private float velX = 0;
    private float velY = 0;
    private float decay = 0.98f;

    public Starship(Texture texture) {
        //super(texture);
        super();
    }

    @Override
    public void update() {

        double radians = this.angle/Math.PI*180;
        if (this.driving) {
            this.velX += Math.cos(radians) * this.thrust;
            this.velY += Math.sin(radians) * this.thrust;
        }

        // apply friction
        this.velX *= decay;
        this.velY *= decay;

        // apply velocities
        getTransform().getPosition().x -= this.velX;
        getTransform().getPosition().y -= this.velY;
        getTransform().getRotation().set(angle, angle);
    }

    @Override
    public void input() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            turn(-1);
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            turn(1);
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            driving = true;
        }

        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            velX *= 0.99999999f;
            velY *= 0.99999999f;
        }

        driving = KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W);

    }

    private void turn(float dir) {
        this.angle += turnSpeed * dir;
    }
}