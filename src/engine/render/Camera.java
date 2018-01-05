package engine.render;

import engine.base.CoreEngine;
import engine.base.Node;
import engine.input.CursorHandler;
import engine.input.KeyboardHandler;
import engine.math.*;
import org.lwjgl.glfw.GLFW;
import engine.util.MathUtil;

/**
 * Created by anarchist on 8/30/16.
 */
public class Camera {
    private Vector2f position;

    private float pitch = 0;
    private float yaw = 0;

    private Node player;

    public Camera() {
        position = new Vector2f(0, 0);
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPlayer(Node player) {
        this.player = player;
    }

    public void update() {
        if (player != null) {
            //position = player.getTransform().getPosition();

            // Smooth the camera using the "Michael Smooth" method
            float posX = MathUtil.Lerp(position.x, this.player.getTransform().getPosition().x, 0.045f);
            float posY = MathUtil.Lerp(position.y, this.player.getTransform().getPosition().y, 0.045f);
            position.x = posX;
            position.y = posY;
        }
    }

    /** Function to translate a point given in screen coordinates to world space. It's the same as GLU gluUnProject, but does not
     * rely on OpenGL. The x- and y-coordinate of vec are assumed to be in screen coordinates (origin is the top left corner, y
     * pointing down, x pointing to the right) as reported by the touch methods in {@link Input}. A z-coordinate of 0 will return a
     * point on the near plane, a z-coordinate of 1 will return a point on the far plane. This method allows you to specify the
     * viewport position and dimensions in the coordinate system expected by {@link GL20#glViewport(int, int, int, int)}, with the
     * origin in the bottom left corner of the screen.
     * @param screenCoords the point in screen coordinates (origin top left)
     * @param viewportX the coordinate of the bottom left corner of the viewport in glViewport coordinates.
     * @param viewportY the coordinate of the bottom left corner of the viewport in glViewport coordinates.
     * @param viewportWidth the width of the viewport in pixels
     * @param viewportHeight the height of the viewport in pixels
     * @return the mutated and unprojected screenCoords {@link Vector3} */
    public Vector3f unproject (Vector3f screenCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        float x = screenCoords.x, y = screenCoords.y;
        x = x - viewportX;
        y = DisplayManager.getWindowHeight() - y - 1;
        y = y - viewportY;
        screenCoords.x = (2 * x) / viewportWidth - 1;
        screenCoords.y = (2 * y) / viewportHeight - 1;
        screenCoords.z = 2 * screenCoords.z - 1;
        Matrix4f invertedView = Matrix4f.invert(MathUtil.createViewMatrix(this), null);
        screenCoords = screenCoords.prj(invertedView);
        return screenCoords;
    }

    /** Function to translate a point given in screen coordinates to world space. It's the same as GLU gluUnProject but does not
     * rely on OpenGL. The viewport is assumed to span the whole screen and is fetched from {@link Graphics#getWidth()} and
     * {@link Graphics#getHeight()}. The x- and y-coordinate of vec are assumed to be in screen coordinates (origin is the top left
     * corner, y pointing down, x pointing to the right) as reported by the touch methods in {@link Input}. A z-coordinate of 0
     * will return a point on the near plane, a z-coordinate of 1 will return a point on the far plane.
     * @param screenCoords the point in screen coordinates
     * @return the mutated and unprojected screenCoords {@link Vector3} */
    public Vector3f unproject (Vector3f screenCoords) {
        unproject(screenCoords, 0, 0, DisplayManager.getWindowWidth(), DisplayManager.getWindowHeight());
        return screenCoords;
    }

//    private Vector4f toEyeCoords(Vector4f clipCoords) {
////        Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
////        Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
////        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
////    }

    private Vector2f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = Matrix4f.invert(MathUtil.createViewMatrix(this), null);
        Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
        Vector2f mouseRay = new Vector2f(rayWorld.x, rayWorld.y);
        mouseRay.normalise();
        return mouseRay;
    }

    public Vector2f screenToWorld(Vector2f mouseCoords) {
//        Vector2f result = mouseCoords.normalise(null);
//        result.x += getPosition().x;
//        result.y += getPosition().y;

        Vector2f normalizedCoords = CursorHandler.getNormalisedDeviceCoordinates();
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
        //Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector2f worldRay = toWorldCoords(clipCoords);

        return worldRay;
        //return new Vector2f((position.x + (mouseCoords.x/DisplayManager.getWindowWidth())) - position.x
        //        , (position.y + (mouseCoords.y/DisplayManager.getWindowHeight())) - position.y);
    }
}
