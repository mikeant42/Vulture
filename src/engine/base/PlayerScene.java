package engine.base;

import engine.physics.PhysicsEngine;
import engine.player.Player;
import engine.render.Camera;

import java.util.ArrayList;
import java.util.List;

public class PlayerScene {
    /*
    This needs to contain a hierarchy of nodes that can be removed from the scene in a click. The scene then
    must have the ability to be *generated* back, not just removed from the scene and added again.

    Two main classes
    - SpaceScene
    - PlanetScene
     */

    private Node rootNode;
    private List<Node> nodes = new ArrayList<>();
    private Seed seed;
    private boolean active = false;
    private PhysicsEngine physicsEngine;

    public PlayerScene(Seed seed) {
        rootNode = new Node("Scene"+seed.seed);
        nodes = rootNode.getAllChildren();
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public void setPhysicsEngine(PhysicsEngine physicsEngine) {
        this.physicsEngine = physicsEngine;
    }

    public void addEntity(Node node) {
        rootNode.addChild(node);

        nodes = rootNode.getAllChildren();
    }

    public void removeEntity(Node node) {
        rootNode.remove(node);

        nodes = rootNode.getAllChildren();
    }

    public void update(Camera camera) {
        List<Node> physicsNodes = new ArrayList<>();

        for (Node node : nodes) {
            if (node.isSolid()) {
                physicsNodes.add(node);
            }
            camera.update();

            node.input();
            node.update();
            node.render();
        }

        physicsEngine.integrate(physicsNodes);
    }

    public Seed getSeed() {
        return seed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
