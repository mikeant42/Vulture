package engine.render.sprite;

import engine.math.Vector2f;
import engine.render.DisplayManager;
import engine.render.texture.Texture;

/**
 * Created by anarchist on 8/31/16.
 */
public class SpriteAnimator {
    private float blendFactor = 0.03f;

    private Texture texture;

    private int currentTextureIndex = 0;

    private float elapsedTime = 0;
    private float currentTime = 0;
    private float lastTime;
    private float fps;

    public SpriteAnimator(Texture texture) {
        this.texture = texture;


        lastTime = (float)getTime();
        fps = 1.0f / 5;
    }

    public float getFps() {
        return fps;
    }

    public void setFps(float fps) {
        this.fps = 1.0f / fps;
    }

    public static double getTime() {
        return (double) System.nanoTime() / (double) 1000000000L;
    }

    private float getTextureXOffset(int index) {
        int column = index % texture.getNumberOfRows();
        return (float)column / (float)texture.getNumberOfRows();
    }

    private float getTextureYOffset(int index) {
        int row = index  / texture.getNumberOfRows();
        return (float)row / (float)texture.getNumberOfRows();
    }

    protected Vector2f getCurrentFrame() {
        return new Vector2f(getTextureXOffset(currentTextureIndex), getTextureYOffset(currentTextureIndex));
    }

//    private void calcCurrentFrame() {
//        //currentTextureIndex += texture.getNumberOfRows();
//        //currentTextureIndex = 5;
//        System.out.println("Current : " + currentTextureIndex);
//    }

    protected float getBlendFactor() {
        return blendFactor;
    }

//
//    protected Vector2f getNextFrame() {
//        return new Vector2f(getTextureXOffset(nextTextureIndex), getTextureYOffset(nextTextureIndex));
//    }

    private int loop(int index, int start, int end) {
        int returnIndex = index;

        currentTime = (float)getTime();
        elapsedTime += currentTime - lastTime;

        if (elapsedTime >= fps) {
            elapsedTime = 0;
            returnIndex++;
            if (returnIndex >= end) {
                returnIndex = start;
            }
        }

        lastTime = currentTime;

        return returnIndex;
    }


    public int loopFrames(int start, int end) {
//        int frame = start;
//        for (int i = start; i < end; i++) {
//            frame++;
//            if (frame > end) {
//                frame = start;
//            }
//            System.out.println(frame);
//        }
//        currentTextureIndex = frame;

        if (currentTextureIndex <= end && currentTextureIndex >= start) {
            currentTextureIndex = loop(currentTextureIndex, start, end);
        } else {
            currentTextureIndex = start;
            //currentTextureIndex = loop(currentTextureIndex, start, end);
        }

        return currentTextureIndex;


    }

    public void setCurrentIndex(int index) {
        this.currentTextureIndex = index;
    }

}
