package engine.player;

import engine.input.KeyboardHandler;
import engine.render.texture.Texture;
import org.lwjgl.glfw.GLFW;

public class ControlledPlayer extends Player {

    public ControlledPlayer(Texture texture) {
        super(texture);

    }

    @Override
    public void input() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            setState(Player.STATE_RUNNING_RIGHT);
        } else if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            setState(Player.STATE_RUNNING_LEFT);
        } else if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            setState(Player.STATE_RUNNING_FORWARD);
        } else if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            setState(Player.STATE_RUNNING_BACK);
        } else {
            setState(Player.STATE_STANDING);
        }

    }
}
