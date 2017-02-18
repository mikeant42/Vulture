package engine.terrain;

import engine.algo.noise.Fractal2D;
import engine.algo.noise.Function2D;
import engine.algo.noise.SimplexNoise;
import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector4f;
import engine.render.Quad;
import engine.render.RawShader;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by anarchist on 9/3/16.
 */
public class Terrain extends Node {

    private Quad quad;
    private RawShader shader;
    private float width, height = 256;

    public Terrain() {
        shader = new RawShader("default.vert", "terrain/terrain.frag");
        float[] positions = gen(0.5f, 0.5f);
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
    }

    private float[] gen(float displace, float roughness){
        float[] coords = new float[512];
        Function2D function = new Fractal2D(new SimplexNoise(52), 6, 0.8f);

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
//                float dx = (x - radius)/(float)radius;  // normalized coordinates -1 -> 1 from left to right
//                float dy = (y - radius)/(float)radius;  // normalized coordinates -1 -> 1 from top to bottom
// Set every pixel in the image to random value.
                float val = function.eval(x, y);
                coords[x]  = val;
            }
        }

        return coords;
    }

    @Override
    public void render() {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, new Vector2f(this.getTransform().getScale(), this.getTransform().getScale()));
        shader.setUniform("transformationMatrix", trans);
        shader.setUniform("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));

        this.shader.loadDefaults();

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

}
