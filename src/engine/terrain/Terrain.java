package engine.terrain;

import engine.algo.noise.Fractal2D;
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
        this(new RawShader("default.vert", "star/stars.frag"));
    }

    public Terrain(RawShader shader) {
        super();

        //float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        float[] positions = genPositions4();
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
        this.shader = shader;
        this.texture = Texture.loadTexture("StoneWall.png");

    }

    private float[] genPositions() {
        float[] positions = new float[512];
        float t = -2;
        for (int i = 0; i < 20; i++) {
            float x = MathUtil.randomNextFloat(0,1);
            float y = t*i;
            positions[i] = x;
            positions[i+1] = 1;
        }
        return positions;
    }

    private float[] genPositions4() {
        int LENGTH = 500;
        int res=2; // No lower than 2

        float x = -2f;     //current position to put vertices
        float med = -1; //starting y
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
            y += (MathUtil.randomNextFloat() - 0.5f) * 50;
            offset +=4;
        }
        return tempVer;
    }

    private float[] genPositions3() {
        Fractal2D noise = new Fractal2D(new SimplexNoise(52), 2, 2f);
        float[] positions = new float[10];
        for (int x =0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                positions[y]=noise.eval(x,y);
                positions[x] = 1;
            }
        }
        return positions;
    }

    private float[] genPositions2(int width, int height, float displace, float roughness) {
        List<Float> positions = new ArrayList<>();
        int power = (int)Math.pow(2, Math.ceil(Math.log(width) / (Math.log(2))));

        positions.add(0,(float)(height/2 + (Math.random()*displace*2) - displace));
        positions.add(power, (float)(height/2 + (Math.random()*displace*2) - displace));
        displace *= roughness;

        // Increase the number of segments
        for(int i = 1; i < power; i *=2){
            // Iterate through each segment calculating the center point
            for(int j = (power/i)/2; j < power; j+= power/i){
                positions.add(j, ((positions.get(j - (power / i) / 2) + positions.get(j + (power / i) / 2) / 2)));
                positions.add(j, (float)(positions.get(j) + Math.random()*displace*2) - displace);
            }
            // reduce our random range
            displace *= roughness;
        }
        float[] floatArray = new float[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            floatArray[i]=positions.get(i);
        }
        return floatArray;
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

        shader.loadDefaults();

        shader.setUniform("resolution", new Vector2f(100, 100));

        shader.setUniform("starDensity", 3.5f);
        shader.setUniform("starRadius", 0.5f);
        shader.setUniform("starColor", new Vector3f(0.796078431372549f, 0.9254901960784314f, 0.9254901960784314f));
        shader.setUniform("spaceColor", new Vector3f(0.15f, 0.15f, 0.15f));
        shader.setUniform("speed", 0.2f);


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

}
