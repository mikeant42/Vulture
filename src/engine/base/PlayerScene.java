package engine.base;

import engine.math.Vector2f;
import engine.network.PlayerData;
import engine.physics.PhysicsEngine;
import engine.player.Player;
import engine.render.Camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerScene {
    /*
    This needs to contain a hierarchy of nodes that can be removed from the scene in a click. The scene then
    must have the ability to be *generated* back, not just removed from the scene and added again.

    Two main classes
    - SpaceScene
    - PlanetScene

    Also needs to be optimized
     */

    private Node rootNode;
    private List<Node> nodes;
    private Seed seed;
    private boolean active = false;
    private PhysicsEngine physicsEngine;
    private Player player;

    private Map<Integer, Player> realPlayers = new HashMap<>();

    public PlayerScene(Seed seed) {
        rootNode = new Node("Scene"+seed.seed);
        nodes = rootNode.getAllChildren();
    }

    public void setControlledPlayer(Player player) {
        this.player = player;
        addEntity(player);
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

        if (isActive()) {
            CoreEngine.getNetworkHandler().setActiveScene(this);
            PlayerData data = new PlayerData();
            Vector2f pos = player.getTransform().getPosition();
            data.posX = pos.x;
            data.posY = pos.y;
            data.clientID = CoreEngine.getNetworkHandler().getClientID();

            data.sceneID = 52;
            CoreEngine.getNetworkHandler().sendPlayerInfo(data);
        }
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

    public void addNewPlayer(int id) {
        
    }
}
