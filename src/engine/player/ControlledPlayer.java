package engine.player;

import engine.base.CoreEngine;
import engine.ground.Projectile;
import engine.input.CursorHandler;
import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import engine.math.Vector2f;
import engine.render.texture.Texture;
import org.lwjgl.glfw.GLFW;

public class ControlledPlayer extends Player {

    public ControlledPlayer(Texture texture) {
        super(texture);

    }

    @Override
    public void update() {
        super.update();
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

        if (MouseHandler.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
            //Vector2f normCoords = new Vector2f(CursorHandler.getMouseDX(), CursorHandler.getMouseDY());
            Vector2f normCoords = CursorHandler.getNormalisedDeviceCoordinates();
            Vector2f spaceCoords = CoreEngine.getCamera().screenToWorld(new Vector2f(normCoords.x, normCoords.y));

            System.out.println(spaceCoords.toString());

            Projectile projectile = new Projectile(Texture.loadTexture("baum.png"), spaceCoords);
            CoreEngine.getScene().addEntity(projectile);
        }

    }
}
