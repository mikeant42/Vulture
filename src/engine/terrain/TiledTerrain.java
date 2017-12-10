package engine.terrain;

import engine.algo.noise.Fractal2D;
import engine.algo.noise.SimplexNoise;
import engine.base.PlayerScene;
import engine.math.Vector2f;
import engine.render.sprite.Sprite;
import engine.render.texture.Texture;
import engine.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class TiledTerrain {
    private List<Sprite> tiles = new ArrayList<>();
    private float grid = 10;
    private float tileSize = 0.1f;

    // Put these in the tile class
    private Texture grassTex = Texture.loadTexture("grass03.png");

    Fractal2D noise = new Fractal2D(new SimplexNoise(30), 2, 0.6f);



    public TiledTerrain() {
        genTiles();
    }

    private void genTiles() {
        float rx = 0;
        for(int x = 0; x < grid; x++){
            for(int y = 0; y < grid; y++){
                float dx = (x - tileSize)/(float)tileSize;  // normalized coordinates -1 -> 1 from left to right
                float dy = (y - tileSize)/(float)tileSize;  // normalized coordinates -1 -> 1 from top to bottom

                float val = noise.eval(dx, dy);
                Sprite tile = new Sprite(grassTex);
                tile.getTransform().setScale(tileSize);
                tile.getTransform().setPosition(new Vector2f(rx, val));
                tiles.add(tile);
                rx+=tileSize;
            }
        }
    }

    public void addToScene(PlayerScene scene) {
        for (Sprite sprite : tiles) {
            scene.addEntity(sprite);
        }
    }

    public List<Sprite> getTiles() {
        return tiles;
    }
}
