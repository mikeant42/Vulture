package engine.base;

import engine.render.Camera;
import engine.render.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarchist on 8/30/16.
 */
public class CoreEngine {
    private static Camera camera;
    private static Node rootNode;
    private static Loader loader;

    private static List<Node> nodes = new ArrayList<>();

    public static void init() {
        camera = new Camera();

        rootNode = new Node("Root");
        nodes = rootNode.getAllChildren();

        loader = new Loader();
    }

    public static Camera getCamera() {
        return camera;
    }

    public static Loader getLoader() {
        return loader;
    }

    public static void addEntity(Node node) {
        rootNode.addChild(node);

        nodes = rootNode.getAllChildren();
    }

    public static void update() {
        for (Node node : nodes) {
            camera.update();

            node.input();
            node.update();
            node.render();
        }
    }
}
