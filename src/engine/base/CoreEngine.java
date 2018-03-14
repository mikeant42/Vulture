package engine.base;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import engine.network.SomeRequest;
import engine.network.SomeResponse;
import engine.render.Camera;
import engine.render.Loader;

import java.io.IOException;
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

    private static ClientNetworkHandler networkHandler;

    public static void init() {
        camera = new Camera();

        loader = new Loader();

        networkHandler = new ClientNetworkHandler();
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

    public static ClientNetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
