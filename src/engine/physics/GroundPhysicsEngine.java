package engine.physics;

import engine.base.Node;
import engine.math.Vector2f;
import engine.player.Player;
import engine.render.sprite.Sprite;
import engine.terrain.Terrain;
import engine.terrain.TiledTerrain;

import java.util.List;

public class GroundPhysicsEngine implements PhysicsEngine {
    private Player player;
    private TiledTerrain terrain;

    public GroundPhysicsEngine(Player player, TiledTerrain terrain) {
        this.player = player;
        this.terrain = terrain;
    }

    @Override
    public void integrate(List<Node> nodes) {
        player.updateBoundingBox();
        /**
         * TODO i need to build a quadtree or some spatial system to reduce the amount of nodes being checked
         */
        for (Node node : nodes) {
            if (node.isSolid()) {
                node.updateBoundingBox();
                // Needs to make sure that the player doesn't collide with itself
                if (player.getBoundingBox().intersects(node.getBoundingBox()) && !player.getClass().equals(node.getClass())) {
                    player.isColliding(node);
                }
            }
        }
    }



    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TiledTerrain getTerrain() {
        return terrain;
    }

    public void setTerrain(TiledTerrain terrain) {
        this.terrain = terrain;
    }
}
