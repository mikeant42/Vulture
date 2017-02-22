package engine.terrain;

import engine.algo.noise.Fractal2D;
import engine.algo.noise.Function2D;
import engine.algo.noise.SimplexNoise;
import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector4f;
import engine.render.Loader;
import engine.render.Quad;
import engine.render.RawShader;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;

/**
 * Created by anarchist on 9/3/16.
 */
public class Terrain extends Node {

    private Quad quad;
    private RawShader shader;
    private float width = 10;
    private float height = 10;

    private static int NUM_METABALLS = 3;

    private ArrayList<MetaBall> metaBalls = new ArrayList<>();

    private int numVerts = 50;
    private float[] terrain_array = new float[numVerts*2];
    private float roughness = 0.55f;
    private Function2D function;

    private float[] positions =new float[] {
            -1.0f,  1.0f, // top left
            -1.0f, -1.0f, // bottom left
            1.0f,  1.0f, // top right
            1.0f, -1.0f, // bottom right
    };

    public Terrain() {
        shader = new RawShader("default.vert", "terrain/terrain.frag");

        this.function = new Fractal2D(new SimplexNoise(10), 6, 0.8f);

        generateTerrain();

        quad = CoreEngine.getLoader().loadToVAO(terrain_array, 2);

        //this.getTransform().setScale(0.3f);

    }

    private float[] buildBalls() {
        for (int i = 0; i < NUM_METABALLS; i++) {
            float radius = MathUtil.randomNextFloat(02, 5);
            metaBalls.add(new MetaBall(
                    new Vector2f(MathUtil.randomNextFloat() * (width - 2 * radius) + radius,
                    MathUtil.randomNextFloat() * (height - 2 * radius) + radius),radius));
        }

        float[] data = new float[3 * NUM_METABALLS];
        for (int i = 0; i < NUM_METABALLS; i++) {
            int baseIndex = 3 * i;
            MetaBall mb = metaBalls.get(i);
            data[baseIndex + 0] = mb.getPosition().x;
            data[baseIndex + 1] = mb.getPosition().y;
            data[baseIndex + 2] = mb.getRadius();
        }

        return data;
    }

    private void generateTerrain() {
        for (int i = 0; i < numVerts; i++) {
            float tl = positions[1];
            float bl = positions[2];
            terrain_array[i] = function.eval(tl, bl);
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

        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, new Vector2f(this.getTransform().getScale(), this.getTransform().getScale()));
        shader.setUniform("transformationMatrix", trans);
        shader.setUniform("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));

        //shader.setUniform("metaBalls", metaData);

        this.shader.loadDefaults();

        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

}
