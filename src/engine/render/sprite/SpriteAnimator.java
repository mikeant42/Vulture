package engine.render.sprite;

import engine.math.Vector2f;
import engine.render.DisplayManager;
import engine.render.texture.Texture;

/**
 * Created by anarchist on 8/31/16.
 */
public class SpriteAnimator {
    private Vector2f frame1;
    private Vector2f frame2;
    private float blendFactor = 0.03f;

    private Texture texture;

    private int currentTextureIndex = 0;
    private int nextTextureIndex = currentTextureIndex;

    private int numFrames;
    private int animation;

    private float elapsedTime = 0;
    private float currentTime = 0;
    private float lastTime;
    private float fps;

    public SpriteAnimator(Texture texture) {
        this.texture = texture;

        frame1 = new Vector2f();
        frame2 = new Vector2f();

        numFrames = texture.getNumberOfRows() * texture.getNumberOfRows();
        animation = texture.getNumberOfRows();


        lastTime = (float)getTime();
        fps = 1.0f / fps;
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

        currentTextureIndex++;
        if (currentTextureIndex >= end) {
            currentTextureIndex = start;
        }
        return currentTextureIndex;
    }

    public void setCurrentIndex(int index) {
        this.currentTextureIndex = index;
    }

}
