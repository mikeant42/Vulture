package engine.player;

import engine.render.DisplayManager;
import engine.render.texture.Texture;

import java.util.Random;

/**
 * This type of player literally just chooses a random direction roams around the world
 */

public class RoamerPlayer extends Player {

    // These are all for timing when he chooses to make a decision-
    private float elapsedTime = 0;
    private float currentTime = 0;
    private float lastTime;
    private float timeToDecide;
    private float directionTime;

    public RoamerPlayer(Texture texture) {
        super(texture);

        lastTime = (float)getTime();
        timeToDecide = 3;
        directionTime = 1;

        setSensitivity(0.5f);
    }

    public static double getTime() {
        return (double) System.nanoTime() / (double) 1000000000L;
    }

    private int chooseRandomDirection() {
        Random r = new Random();

        int[] randDir = new int[5];
        randDir[0] = Player.STATE_STANDING;
        randDir[1] = Player.STATE_RUNNING_FORWARD;
        randDir[2] = Player.STATE_RUNNING_BACK;
        randDir[3] = Player.STATE_RUNNING_LEFT;
        randDir[4] = Player.STATE_RUNNING_RIGHT;

        int choice = r.nextInt(5);

        return randDir[choice];
    }

    @Override
    public void update() {
        super.update();

        currentTime = (float)getTime();
        elapsedTime += currentTime - lastTime;

        if (elapsedTime >= timeToDecide) {
            setState(chooseRandomDirection());
            elapsedTime = 0;
        } else if (elapsedTime >= directionTime) {
            setState(Player.STATE_STANDING);
        }

        lastTime = currentTime;

    }
}
