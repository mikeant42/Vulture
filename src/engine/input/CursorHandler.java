package engine.input;

/**
 * Created by anarchist on 6/15/16.
 */
import engine.math.Vector2f;
import engine.render.DisplayManager;
import org.lwjgl.glfw.GLFWCursorPosCallback;


public class CursorHandler extends GLFWCursorPosCallback {

    private static float mouseX;
    private static float mouseY;

    private static float mouseDX;
    private static float mouseDY;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        // TODO Auto-generated method stub
        // this basically just prints out the X and Y coordinates
        // of our mouse whenever it is in our window
        ///System.out.println("X: " + xpos + " Y: " + ypos);

        mouseDX = (float)(xpos - mouseX);
        mouseDY = (float)(ypos - mouseY);

        mouseX = (float)xpos;
        mouseY = (float)ypos;

    }

    public static float getMouseX() {
        return mouseY;
    }

    public static float getMouseY() {
        return mouseY;
    }

    public static float getMouseDX() {
        return mouseDX;
    }

    public static float getMouseDY() {
        return mouseDY;
    }
//
//    public static Vector2f getNormalizedCoords() {
//        float normalizeX =  -1.0f + 2.0f * getMouseX() / DisplayManager.getWindowWidth();
//        float normalizeY =  1.0f - 2.0f  * getMouseY() / DisplayManager.getWindowHeight();
//        return new Vector2f(normalizeX, normalizeY);
//    }

    public static  Vector2f getNormalisedDeviceCoordinates() {
        float x = (2.0f * getMouseX()) / DisplayManager.getWindowWidth() - 1f;
        float y = (2.0f * getMouseY()) / DisplayManager.getWindowHeight() - 1f;
        return new Vector2f(x, y); // If doesn't work try changing to -y
    }

}