package engine.render.sprite;

import engine.base.CoreEngine;
import engine.base.Node;
import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.render.Quad;
import engine.render.RawShader;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by anarchist on 8/28/16.
 */
public class Sprite extends Node {

    private Quad quad;
    private RawShader shader;
    private Texture texture;

    private SpriteAnimator spriteAnimator;

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

    protected SpriteAnimator getSpriteAnimator() {
        return this.spriteAnimator;
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
