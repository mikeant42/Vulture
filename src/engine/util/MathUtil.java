package engine.util;

import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.render.Camera;
import engine.render.DisplayManager;

import java.util.Random;

/**
 * Created by anarchist on 8/28/16.
 */
public class MathUtil {

    private static Random random = new Random();

    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Vector2f translation, float ry, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y  * DisplayManager.getAspectRatio(), 1f), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.translate(new Vector2f(), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();

        Matrix4f.rotate((float)Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float)Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
        // TODO - Add in roll

        Vector2f cameraPos = camera.getPosition();
        Vector2f negativeCameraPos = new Vector2f(-cameraPos.x, -cameraPos.y);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);

        return viewMatrix;
    }

    public static float randomNextFloat() {
        return random.nextFloat();
    }

    public static float randomNextFloat(float a, float b) {
        float random = randomNextFloat();
        float diff = b - a;
        float r = random * diff;
        return a + r;
    }

    // Used for smoothing
    public static float Lerp(float value1, float value2, float amount) {
        return value1 + (value2 - value1) * amount;
    }

}
