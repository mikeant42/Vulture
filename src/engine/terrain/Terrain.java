package engine.terrain;

import engine.algo.noise.Fractal2D;
import engine.algo.noise.Function2D;
import engine.algo.noise.SimplexNoise;
import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.render.Quad;
import engine.render.RawShader;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarchist on 1/3/17.
 */
public class Terrain extends Node {

    private Quad quad;
    private RawShader shader;
    private Texture texture;

    public Terrain () {
        this(new RawShader("default.vert", "terrain/terrain.frag"));
        texture = Texture.loadTexture("grass03.png");
    }

    public Terrain(RawShader shader) {
        super();

        //float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        float[] positions = genPositions();
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
        this.shader = shader;
        this.texture = Texture.loadTexture("StoneWall.png");

    }

    private float[] genPositions() {
        Fractal2D noise = new Fractal2D(new SimplexNoise(30), 2, 0.6f);

        int LENGTH = 500;
        int res=512; // No lower than 2

        float x = -3f;     //current position to put vertices
        float med = 3; //starting y
        float y = med;

        float slopeWidth = (float) (LENGTH / ((float) (res - 1))); //horizontal distance between 2 heightpoints


        // VERTICES
        float[] tempVer = new float[2*2*res]; //hold vertices before setting them to the mesh
        int offset = 0; //offset to put it in tempVer

        for (int i = 0; i<res; i++) {

            tempVer[offset+0] = x;      tempVer[offset+1] = 0f; // below height
            tempVer[offset+2] = x;      tempVer[offset+3] = y;  // height

            //next position:
            x += slopeWidth;
            //y += (MathUtil.randomNextFloat() - 0.5f) * 50;
            y += noise.eval(x,y);
            offset +=4;
        }
        return tempVer;
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
        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, new Vector2f(this.getTransform().getScale(), this.getTransform().getScale()));
        shader.setUniform("transformationMatrix", trans);
        shader.setUniform("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

}
