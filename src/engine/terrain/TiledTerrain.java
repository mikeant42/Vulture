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
    private int grid = 3;
    private float tileSize = 0.3f;


    //private Sprite[][] world = new Sprite[grid][grid];

    // Put these in the tile class
    private Texture grassTex = Texture.loadTexture("grass03.png");
    private Texture water1Tex = Texture.loadTexture("watertile1.png");

    Fractal2D noise = new Fractal2D(new SimplexNoise(30), 2, 0.6f);



    public TiledTerrain() {
        water1Tex.setNumberOfRows(2);
        genTiles();
    }

    private Sprite createTile(float type, float x, float y) {
        Sprite tile;
        if (type > 0.5f) {
            tile = new Sprite(grassTex);
            tile.getTransform().setScale(tileSize);
            tile.getTransform().setPosition(new Vector2f(x, y));
        } else {
            tile = new Sprite(water1Tex);
            tile.getTransform().setScale(tileSize);
            tile.getTransform().setPosition(new Vector2f(x, y));
        }

        return tile;
    }

    private void genTiles() {
        for (float i = 0; i < grid; i+=tileSize) {
            for (float j = 0; j < grid; j+=tileSize) {
                float val = noise.eval(i,j);
                //System.out.println(val);
                tiles.add(createTile(val, i, j));
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
