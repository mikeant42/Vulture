package engine.render.space.planet;

import engine.base.CoreEngine;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.math.Vector4f;
import engine.render.Quad;
import engine.render.RawShader;
import engine.algo.noise.Function2D;
import engine.render.texture.Texture;
import engine.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by anarchist on 1/18/17.
 */
public class GasPlanet extends Planet {

    private Quad quad;
    private RawShader shader;

    private Texture texture;

    // Texture size
    private int textureSize;

    private float radius;
    private Function2D function;


    private float rotationSpeed = 0.02f;
    private float planetRadius = 0.5f;
    private Vector2f center = new Vector2f(0.5f, 0.5f);
    private float atmosphereBorder = 0.05f;
    private Vector3f atmosphereColor = new Vector3f(0.2f, 0.4f, 0.5f);

    private String name;

    public GasPlanet() {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = CoreEngine.getLoader().loadToVAO(positions, 2);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected void setName(String name) {
        this.name = name;
    }

    public void generate(String colorMap, Function2D function) {
        this.shader = new RawShader("default.vert", "planet/gasplanet.frag", colorMap);

        this.textureSize = 512;
        this.radius = textureSize/2;

        //this.function = new Fractal2D(new VoronoiNoise(this.seed, (short)1), 6, 0.9f);
        //this.function = new Fractal2D(new SimplexNoise(this.seed), 6, 0.8f);
        this.function = function;

        this.texture = new Texture(textureSize, textureSize, this.buildTexture(this.radius, this.textureSize, this.function));

    }


    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getTextureSize() {
        return textureSize;
    }

    public void setTextureSize(int size) {
        this.textureSize = size;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public float getPlanetRadius() {
        return planetRadius;
    }

    @Override
    public void setPlanetRadius(float planetRadius) {
        this.planetRadius = planetRadius;
    }

    @Override
    public Vector2f getCenter() {
        return center;
    }

    @Override
    public void setCenter(Vector2f center) {
        this.center = center;
    }

    @Override
    public float getAtmosphereBorder() {
        return atmosphereBorder;
    }

    @Override
    public void setAtmosphereBorder(float atmosphereBorder) {
        this.atmosphereBorder = atmosphereBorder;
    }

    @Override
    public Vector3f getAtmosphereColor() {
        return atmosphereColor;
    }

    @Override
    public void setAtmosphereColor(Vector3f atmosphereColor) {
        this.atmosphereColor = atmosphereColor;
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

        this.shader.loadDefaults();

        shader.setUniform("radius", planetRadius);
        shader.setUniform("center", center);
        shader.setUniform("atmosphereBorder", atmosphereBorder);
        shader.setUniform("atmosphereColor", new Vector4f(atmosphereColor.x, atmosphereColor.y, atmosphereColor.z, 1));

        shader.setTextureSlot("noiseSample", 0);


        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }


    @Override
    public void update() {
        this.getTransform().setRotation(new Vector2f(this.getTransform().getRotation().x + 0.008f, this.getTransform().getRotation().y));
    }

}
