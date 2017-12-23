package engine.ground;

import engine.base.CoreEngine;
import engine.math.Vector2f;
import engine.player.Player;
import engine.render.DisplayManager;
import engine.render.sprite.Sprite;
import engine.render.texture.Texture;

public class Projectile extends Sprite {

    private float speed;
    private float sensitivity = 0.8f;
    private Vector2f direction;
    private float duration = 5;

    private float elapsedTime = 0;
    private float currentTime = 0;
    private float lastTime;

    public Projectile(Texture texture, Vector2f direction) {
        super(texture);

        this.direction = direction;

        setIsSolid(true);
        getTransform().setScale(0.2f);

        lastTime = (float)getTime();

    }

    public static double getTime() {
        return (double) System.nanoTime() / (double) 1000000000L;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public void update() {
        speed = sensitivity * (float) DisplayManager.getFrameTimeSeconds();


        currentTime = (float)getTime();
        elapsedTime += currentTime - lastTime;

        if (elapsedTime >= duration) {
            // This code is really bad, needs to be improved. Just asking for bugs
            getParent().remove(this);
            CoreEngine.getScene().removeEntity(this);
            elapsedTime = 0;
        } else {
            //getTransform().getPosition().x += speed * direction.x;
            //getTransform().getPosition().y += speed * direction.y;
            getTransform().setPosition(direction);
        }
        lastTime = currentTime;
    }
}
