package engine.render.space;

import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
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
 */
public class Space extends Node {

    private Quad quad;
    private RawShader shader;
    private Texture texture;

    public Space() {
        this(new RawShader("default.vert", "star/stars.frag"));
    }

    public Space(RawShader shader) {
        super();

        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
        this.shader = shader;
        this.texture = Texture.loadTexture("StoneWall.png");

    }

//    private Texture generateTexture(int width, int height, float density, float brightness,
//                    float prng) {
//        int count = Math.round(width * height * density);
//        int[] data = new int[width * height * 3];
//
//        for (int i = 0; i < count; i++) {
//            int r = (int)Math.floor(prng * width * height);
//            int c = (int)Math.round(255 * Math.log(1-prng) * brightness);
//
//            data[r * 3 + 0] = c;
//            data[r * 3 + 1] = c;
//            data[r * 3 + 2] = c;
//        }
//
//        return data;
//
//    }


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
        shader.setUniformMat4("transformationMatrix", trans);
        shader.setUniformMat4("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));

        shader.loadDefaults();

        shader.setUniformVec2("resolution", new Vector2f(100, 100));

        shader.setUniformFloat("starDensity", 3.5f);
        shader.setUniformFloat("starRadius", 0.5f);
        shader.setUniformVec3("starColor", new Vector3f(0.796078431372549f, 0.9254901960784314f, 0.9254901960784314f));
        shader.setUniformVec3("spaceColor", new Vector3f(0.15f, 0.15f, 0.15f));
        shader.setUniformFloat("speed", 0.2f);


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

}
