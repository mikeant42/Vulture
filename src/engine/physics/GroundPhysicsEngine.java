package engine.physics;

import engine.player.Player;
import engine.terrain.Terrain;

public class GroundPhysicsEngine implements PhysicsEngine {
    private Player player;
    private Terrain terrain;

    public GroundPhysicsEngine(Player player, Terrain terrain) {
        this.player = player;
        this.terrain = terrain;
    }

    public void integrate() {
        //System.out.println("Player pos: " + player.getTransform().getPosition().y);
        //System.out.println("Terrain h" + terrain.getHeight((int)player.getTransform().getPosition().x));


//        if (player.getTransform().getPosition().y > terrain.getHeight((int)player.getTransform().getPosition().x)) {
//            System.out.println("above");
//        }
    }



    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
