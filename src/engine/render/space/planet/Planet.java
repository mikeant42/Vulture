package engine.render.space.planet;

import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.math.Vector4f;
import engine.render.DisplayManager;
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
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by anarchist on 1/4/17.
 */
public class Planet extends Node {

    private Quad quad;
    private RawShader shader;

    private int seed;
    private BufferedImage img;
    private Texture texture;

    private int size;
    private float radius;
    private Function2D function;

    public Planet() {

        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);

        this.shader = new RawShader("default.vert", "planet/planet.frag");
        this.seed = 10;

        this.size = 256;
        this.radius = size/2;

        this.function = new Fractal2D(new Simplex2D(), 6, 0.5f);

        buildTex();
        this.texture = getPermTex();
    }

    public void buildTex() {
        this.img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < img.getWidth(); x++){
            for(int y = 0; y < img.getHeight(); y++){
                float dx = (x - radius)/(float)radius;  // normalized coordinates -1 -> 1 from left to right
                float dy = (y - radius)/(float)radius;  // normalized coordinates -1 -> 1 from top to bottom
// Set every pixel in the image to random value.
                float val = function.eval(dx,dy);
                int rgb = Color.HSBtoRGB(0.0f,0.0f,val);
                img.setRGB(x, y, rgb);
            }
        }


    }

    @Override
    public void render() {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        texture.bind(GL13.GL_TEXTURE0);
        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, new Vector2f(this.getTransform().getScale(), this.getTransform().getScale()));
        shader.setUniform("transformationMatrix", trans);
        shader.setUniform("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));

//        shader.setUniform("time", (float) DisplayManager.getTime());
//        shader.setUniform("resolution", new Vector2f(100, 100));
//
//        shader.setUniform("starDensity", 3.5f);
//        shader.setUniform("starRadius", 0.5f);
//        shader.setUniform("starColor", new Vector3f(0.796078431372549f, 0.9254901960784314f, 0.9254901960784314f));
//        shader.setUniform("spaceColor", new Vector3f(0, 0, 0));
//        shader.setUniform("speed", 0.2f);


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

    public Texture getPermTex() {
//        File outputfile = new File("imagge.jpg");
//        try {
//            ImageIO.write(img, "jpg", outputfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new Texture(size, size, img);
    }

}
