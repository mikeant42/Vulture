package engine.base;

import engine.render.Camera;
import engine.render.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarchist on 8/30/16.
 * The PlayerScene must be set
 */
public class CoreEngine {
    private static Camera camera;

    private static Loader loader;

    private static PlayerScene scene;

    public static void init() {
        camera = new Camera();

        loader = new Loader();
    }

    public static Camera getCamera() {
        return camera;
    }

    public static Loader getLoader() {
        return loader;
    }

    public static PlayerScene getScene() {
        return scene;
    }

    public static void setScene(PlayerScene scenee) {
        if (scene != null) {
            scene.setActive(false);
        }
        scene = scenee;
        scene.setActive(true);
    }

//    public static void addEntities(List<Node> node) {
//        rootNode.addChildren(nodes);
//
//        nodes = rootNode.getAllChildren();
//    }

    public static void update() {
        scene.update(camera);
    }
}
