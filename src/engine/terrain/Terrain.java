package engine.terrain;

import engine.algo.noise.Fractal2D;
import engine.algo.noise.SimplexNoise;
import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.render.Quad;
import engine.render.shader.RawShader;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by anarchist on 1/3/17.
 *
 * Some collision notes
 * - getHeight(x) - returns float y value
 */
public class Terrain extends Node {

    private Quad quad;
    private RawShader shader;
    private Texture texture;
    private float[] heights;
    private float[] vertices;

    int LENGTH = 500;
    int res=512; // No lower than 2

    Fractal2D noise = new Fractal2D(new SimplexNoise(30), 2, 0.6f);


    public Terrain () {
        this(new RawShader("default.vert", "terrain/terrain.frag"));
        texture = Texture.loadTexture("grass03.png");
    }

    public Terrain(RawShader shader) {
        super();

        //float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        vertices = genPositions();
        quad = CoreEngine.getLoader().loadToVAO(vertices, 2);
        this.shader = shader;
        this.texture = Texture.loadTexture("StoneWall.png");

//        System.out.println("^^^^Heights^^^^^^");
//        for (int i = 0; i < heights.length; i++) {
//            System.out.println(heights[i]);
//        }
//
//        System.out.println("^^^^^Vertices^^^^^^");
//        for (int i = 0; i < vertices.length; i++) {
//            System.out.println(vertices[i]);
//        }

    }

//    public float getHeight(int x) {
//        float y=-1;
//        for (int i = 0; i < vertices.length; i++) {
//            float xx = vertices[i];
//            float yy = vertices[i+1];
//            if (xx == x) {
//                y = yy;
//                break;
//            }
//            i+=3;
//        }
//        return y;
//    }


    float lerp(float a, float b, float f)
    {
        return (float) (a * (1.0 - f)) + (b * f);
    }

    private float[] genPositions() {



        float x = -3f;     //current position to put vertices
        float med = 3; //starting y
        float y = med;

        float slopeWidth = (float) (LENGTH / ((float) (res - 1))); //horizontal distance between 2 heightpoints


        // VERTICES
        float[] tempVer = new float[2*2*res]; //hold vertices before setting them to the mesh
        int offset = 0; //offset to put it in tempVer
        heights = new float[res];

        for (int i = 0; i<res; i++) {

            tempVer[offset+0] = x;      tempVer[offset+1] = 0f; // below height
            tempVer[offset+2] = x;      tempVer[offset+3] = y;  // height

            //tempVer[offset] = x;
            //tempVer[offset+1] = y;

            heights[i] = y;

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
        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, this.getTransform().getFullScale());
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
