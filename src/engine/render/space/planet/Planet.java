package engine.render.space.planet;

import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.math.Vector4f;
import engine.render.Quad;
import engine.render.RawShader;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by anarchist on 1/4/17.
 */
public class Planet extends Node {

    private Quad quad;
    private RawShader shader;

    private int seed;
    private BufferedImage img;
    private Texture texture;
    private Texture colorTex;

    private int size;
    private float radius;
    private Function2D function;

    private float rotationSpeed = 0.02f;

    public Planet() {

        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);

        this.shader = new RawShader("default.vert", "planet/planet.frag");

        this.seed = 10;

        this.size = 256;
        this.radius = size/2;

        this.function = new Fractal2D(new Simplex2D(), 6, 0.7f);

        buildTex();
        this.texture = getPermTex();
        this.colorTex = Texture.loadTexture("earth_like.png");
    }

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

    public void buildTex() {
        this.img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

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


    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void render() {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bind();

        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        colorTex.bind();

        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, new Vector2f(this.getTransform().getScale(), this.getTransform().getScale()));
        shader.setUniform("transformationMatrix", trans);
        shader.setUniform("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));

        this.shader.loadDefaults();

        shader.setUniform("radius", 0.5f);
        shader.setUniform("center", new Vector2f(0.5f, 0.5f));
        shader.setUniform("atmosphereBorder", 0.05f);
        shader.setUniform("atmosphereColor", new Vector4f(0.1f, 0.5f, 0.5f, 1));

        shader.setTextureSlot("noiseSample", 0);
        shader.setTextureSlot("colorSample", 1);


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    private Texture getPermTex() {
//        File outputfile = new File("imagge.png");
//        try {
//            ImageIO.write(img, "png", outputfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new Texture(size, size, img);
    }

    @Override
    public void update() {
        this.getTransform().setRotation(new Vector2f(this.getTransform().getRotation().x + 0.02f, this.getTransform().getRotation().y));
    }

}
