package engine.physics;

import engine.math.Vector2f;
import engine.player.Player;
import engine.terrain.Terrain;
import engine.terrain.TiledTerrain;

public class GroundPhysicsEngine implements PhysicsEngine {
    private Player player;
    private TiledTerrain terrain;

    public GroundPhysicsEngine(Player player, TiledTerrain terrain) {
        this.player = player;
        this.terrain = terrain;
    }

    public void integrate() {
        //System.out.println("Player pos: " + player.getTransform().getPosition().y);
        //System.out.println("Terrain h" + terrain.getHeight((int)player.getTransform().getPosition().x));


//        if (player.getTransform().getPosition().y > terrain.getHeight((int)player.getTransform().getPosition().x)) {
//            System.out.println("above");
//        }

        //player.getTransform().setPosition(new Vector2f(player.getTransform().getPosition().x, terrain.getHeight(player.getTransform().getPosition().x)));
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
