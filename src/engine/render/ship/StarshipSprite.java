//package engine.render.ship;
//
//import engine.base.CoreEngine;
//import engine.base.Node;
//import engine.math.Matrix4f;
//import engine.math.Vector2f;
//import engine.render.Quad;
//import engine.render.shader.RawShader;
//import engine.util.MathUtil;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL13;
//import org.lwjgl.opengl.GL20;
//import org.lwjgl.opengl.GL30;
//
///**
// * Created by anarchist on 2/12/2017.
// */
//public class StarshipSprite extends Node {
//    private Quad quad;
//
//    private RawShader shader;
//
//    //private Texture texture;
//
//    // Texture size
//    //private int textureSize;
//
//    private float radius = 0.4f;
//    private Vector2f center = new Vector2f(0.5f, 0.5f);
//
//    public StarshipSprite() {
//        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
//        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
//
//        this.shader = new RawShader("default.vert", "ship/ship.frag");
//    }
//
//    @Override
//    public void render() {
//        shader.start();
//        GL30.glBindVertexArray(quad.getVaoID());
//        GL20.glEnableVertexAttribArray(0);
//
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        //texture.bind();
//
//        Matrix4f trans = MathUtil.createTransformationMatrix(this.getTransform().getPosition(), getTransform().getRotation().x, this.getTransform().getFullScale());
//        shader.setUniform("transformationMatrix", trans);
//        shader.setUniform("viewMatrix", MathUtil.createViewMatrix(CoreEngine.getCamera()));
//
//        this.shader.loadDefaults();
//
//        shader.setUniform("radius", radius);
//        shader.setUniform("center", center);
//
//
//        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
//
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL20.glDisableVertexAttribArray(0);
//        GL30.glBindVertexArray(0);
//        shader.stop();
//    }
//
//}
