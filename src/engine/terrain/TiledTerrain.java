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
    private int grid = 2;
    private float tileSize = 1f;

    private int numTiles = 100;

    //private Sprite[][] world = new Sprite[grid][grid];

    // Put these in the tile class
    private Texture grassTex = Texture.loadTexture("grass03.png");

    Fractal2D noise = new Fractal2D(new SimplexNoise(30), 2, 0.6f);



    public TiledTerrain() {
        genTiles();
    }

    private Sprite createTile(float x, float y) {
        Sprite tile = new Sprite(grassTex);
        tile.getTransform().setScale(tileSize);
        tile.getTransform().setPosition(new Vector2f(x, y));
        return tile;
    }

    private void genTiles() {
//        float rx = 0;
//        for(int x = 0; x < grid; x++){
//            for(int y = 0; y < grid; y++){
//                float dx = (x - tileSize)/(float)tileSize;  // normalized coordinates -1 -> 1 from left to right
//                float dy = (y - tileSize)/(float)tileSize;  // normalized coordinates -1 -> 1 from top to bottom
//
//                float val = noise.eval(x, y);
//                Sprite tile = new Sprite(grassTex);
//                tile.getTransform().setScale(tileSize);
//                tile.getTransform().setPosition(new Vector2f(rx, val));
//                tiles.add(tile);
//                rx+=tileSize+0.1f;
//            }
//        }

//        for (int x = 0; x < grid; x+=0.1f) {
//            for (int y = 0; y < grid; y+=0.1f) {
//                //float val = noise.eval(x,y);
//                world[x][y] = createTile(x,y);
//            }

        int rx = 0;
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                tiles.add(createTile(i, j));
                rx += 0.1f;
            }
        }

    }

    public void addToScene(PlayerScene scene) {
        for (Sprite sprite : tiles) {
            scene.addEntity(sprite);
        }
//        for(int i=0; i<world.length; i++) {
//            for (int j = 0; j < world[i].length; j++) {
//                scene.addEntity(world[i][j]);
//            }
//        }
    }

//    public List<Sprite> getTiles() {
//        return tiles;
//    }
}
