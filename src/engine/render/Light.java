package engine.render;

import engine.base.CoreEngine;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.math.Vector4f;
import engine.render.shader.RawShader;
import engine.render.shader.Uniform;
import engine.render.sprite.Sprite;
import engine.render.sprite.SpriteAnimator;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Light extends Sprite {
    private Quad quad;
    private RawShader shader;
    private Vector4f lightColor;

    public Light(Vector4f lightColor) {
        super();

        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
        this.shader = new RawShader("light/light.vert", "light/light.frag");

        this.lightColor = lightColor;
        this.getTransform().setScale(0.2f);

    }

    @Override
    public void render() {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, this.getTransform().getFullScale());
        shader.setUniformMat4("transformationMatrix", trans);
        shader.setUniformMat4("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));

        shader.setUniformVec4("lightColor", lightColor);
        shader.loadDefaults();


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Reset to normal blend function
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
}
