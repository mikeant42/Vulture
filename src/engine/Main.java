package engine;

import engine.base.CoreEngine;
import engine.base.Player;
import engine.math.Vector2f;
import engine.render.DisplayManager;
import engine.render.space.planet.Planet;
import engine.render.space.Space;
import engine.render.texture.Texture;

/**
 * Created by anarchist on 8/28/16.
 */
public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay("Vulture Engine");


        CoreEngine.init();

        Texture spriteTex = Texture.loadTexture("blackmage_m.png");
        spriteTex.setNumberOfRows(4);
        Player player = new Player(spriteTex);
        player.getTransform().setScale(0.08f);

        Planet planet = new Planet();
        planet.buildTex();

        planet.getTransform().setPosition(new Vector2f(0,0));
        //planet.getTransform().setScale(1.5f);


        Space thing = new Space();
        thing.getTransform().setScale(5f);
        thing.getTransform().setPosition(new Vector2f(0,0));
        CoreEngine.addEntity(thing);

        CoreEngine.addEntity(planet);



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
