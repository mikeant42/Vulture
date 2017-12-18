package engine.render.sprite;

import engine.base.CoreEngine;
import engine.base.Node;
import engine.math.Matrix4f;
import engine.render.Quad;
import engine.render.shader.RawShader;
import engine.render.shader.Uniform;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.*;

/**
 * Created by anarchist on 8/28/16.
 */
public class Sprite extends Node {

    private Quad quad;
    private RawShader shader;
    private Texture texture;

    private SpriteAnimator spriteAnimator;

    private List<Uniform> uniforms = new ArrayList<>();

    public Sprite(Texture texture) {
        this(texture, new RawShader("main.vert", "main.frag"));
    }

    public Sprite(Texture texture, RawShader shader) {
        super();

        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
        this.shader = shader;

        this.texture = texture;
        spriteAnimator = new SpriteAnimator(texture);
    }

    public SpriteAnimator getSpriteAnimator() {
        return this.spriteAnimator;
    }

    private void addUniform(String name, Object value) {
        uniforms.put(name, value);
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


        for (int i = 0; i < uniforms.size(); i++) {

        }

        //
        shader.setUniform("numberOfRows", texture.getNumberOfRows());
        shader.setUniform("offset", spriteAnimator.getCurrentFrame());
        //shader.setUniform("offset2", spriteAnimator.getNextFrame());
        shader.setUniform("blend", spriteAnimator.getBlendFactor());


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }


}
