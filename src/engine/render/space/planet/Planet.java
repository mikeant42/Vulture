package engine.render.space.planet;

import engine.base.Node;
import engine.base.Seed;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.algo.noise.Function2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by anarchist on 1/18/17.
 */
public abstract class Planet extends Node {

    public Planet() {

    }

    public abstract void generate(String colorMap, Function2D function);

    public abstract float getRotationSpeed();
    public abstract void setRotationSpeed(float speed);

    public abstract float getPlanetRadius();
    public abstract void setPlanetRadius(float speed);

    public abstract float getAtmosphereBorder();
    public abstract void setAtmosphereBorder(float speed);

    public abstract Vector2f getCenter();
    public abstract void setCenter(Vector2f center);

    public abstract int getTextureSize();
    public abstract void setTextureSize(int size);

    public abstract Vector3f getAtmosphereColor();
    public abstract void setAtmosphereColor(Vector3f color);

    public abstract String getName();
    protected abstract void setName(String name);


    // Distorts the texture at the poles to give the impression of being spherical
    private Vector2f distort(float x, float y) {
        float z;
        float tmp, rx, ry;
        float ri = 0.6f; // refraction index

        Vector2f dest = new Vector2f();

        z = (float)Math.sqrt(1 - x * x  - y * y);

        rx = ri * x;
        tmp = (float)Math.sqrt(1 - rx * rx - y * y);
        dest.x = rx * (1 - y * y) / (z * tmp + rx * x);

        ry = ri * y;
        tmp = (float)Math.sqrt(1 - x * x - ry * ry);
        dest.y = ry * (1 - x * x) / (z * tmp + ry * y);

        return dest;
    }

    protected Vector3f genRandomAtmoColor(Seed seed) {
        Random random = new Random();
        random.setSeed(seed.seed);
        return new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random());
    }

    // Actually builds the texture. Might want to relocate to Fractal2D class, but also important to
    // separate.
    protected BufferedImage buildTexture(float radius, int size, Function2D function) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < img.getWidth(); x++){
            for(int y = 0; y < img.getHeight(); y++){
                float dx = (x - radius)/(float)radius;  // normalized coordinates -1 -> 1 from left to right
                float dy = (y - radius)/(float)radius;  // normalized coordinates -1 -> 1 from top to bottom
// Set every pixel in the image to random value.
                Vector2f coords = distort(dx, dy);
                float val = function.eval(coords.x, coords.y);
                int rgb = Color.HSBtoRGB(0.0f,0.0f,val);
                img.setRGB(x, y, rgb);
            }
        }

        return img;

    }

    /*
    TODO - move this to a math class or something
     */
    public boolean isHovering(Vector2f loc) {
        float dx = getTransform().getPosition().x - loc.x;
        float dy = getTransform().getPosition().y - loc.y;
        float r = getPlanetRadius() + 0.2f;
        return dx * dx + dy * dy <= r * r;
    }


}
