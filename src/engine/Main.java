package engine;

import engine.base.CoreEngine;
import engine.base.Player;
import engine.math.Vector2f;
import engine.render.DisplayManager;
import engine.render.RawShader;
import engine.render.space.Space;
import engine.render.sprite.Sprite;
import engine.render.texture.Texture;
import engine.util.MathUtil;

/**
 * Created by anarchist on 8/28/16.
 */
public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay("Vulture Engine");


        CoreEngine.init();

        Texture terrainTex = Texture.loadTexture("grass03.png");
        Sprite terrainSprite = new Sprite(terrainTex, new RawShader("terrain/terrain.vert", "terrain/terrain.frag"));
        terrainSprite.getTransform().setPosition(new Vector2f(0.0f, 0.0f));
        terrainSprite.getTransform().setScale(150f);
        CoreEngine.addEntity(terrainSprite);

        Texture spriteTex = Texture.loadTexture("blackmage_m.png");
        spriteTex.setNumberOfRows(4);
        Player player = new Player(spriteTex);
        player.getTransform().setScale(0.08f);

        Texture treeTex = Texture.loadTexture("baum.png");

        for (int i = 0; i < 15; i++) {
            Sprite treeSprite = new Sprite(treeTex);
            treeSprite.getTransform().setScale(0.2f);
            treeSprite.getTransform().setPosition(new Vector2f(MathUtil.randomNextFloat(-2.0f, 2.0f), MathUtil.randomNextFloat(-2.0f, 2.0f)));
            CoreEngine.addEntity(treeSprite);
        }


        Space thing = new Space();
        //thing.getTransform().setScale(0.2f);
        thing.getTransform().setPosition(new Vector2f(0,0));
        CoreEngine.addEntity(thing);


        //sprite.addChild(charSprite);
        CoreEngine.addEntity(player);
        CoreEngine.getCamera().setPlayer(player);

        //sprite.getTransform().setPosition(new Vector2f(-0.5f, 0.2f));

        while (!DisplayManager.getShouldWindowClose()) {

            CoreEngine.update();


            DisplayManager.loop();
        }

        DisplayManager.closeDisplay();

    }
}
