package engine;

import engine.base.CoreEngine;
import engine.base.PlayerScene;
import engine.base.Seed;
import engine.player.Player;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.render.DisplayManager;
import engine.render.gui.font.FontType;
import engine.render.gui.font.GUIText;
import engine.render.gui.font.TextManager;
import engine.render.space.Space;
import engine.render.space.planet.PlanetGenerator;
import engine.render.texture.Texture;
import engine.terrain.Terrain;

import java.io.File;

/**
 * Created by anarchist on 8/28/16.
 */
public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay("Vulture Engine");

        Seed seed = new Seed();
        seed.seed = 52;
        PlayerScene spaceScene = new PlayerScene(seed);


        CoreEngine.init();

        TextManager.init(CoreEngine.getLoader());

        Texture spriteTex = Texture.loadTexture("blackmage_m.png");
        spriteTex.setNumberOfRows(4);
        Player player = new Player(spriteTex);
        player.getTransform().setScale(0.1f);

        PlanetGenerator planetGen = new PlanetGenerator(PlanetGenerator.PLANET_TYPE_HABIT);


        Space thing = new Space();
        thing.getTransform().setScale(5f);
        thing.getTransform().setPosition(new Vector2f(0,0));
        spaceScene.addEntity(thing);

        spaceScene.addEntity(planetGen.getPlanet());

        FontType font = new FontType(Texture.loadTexture("font/BadMofo.png").getTextureID(), new File("res/tex/font/BadMofo.fnt"));

        GUIText text = new GUIText("Planet: ", 1.5f, font, new Vector2f(0.03f,0.92f), 1f, false);
        text.setColor(new Vector3f(1,1,1));

        TextManager.loadText(text);

        //sprite.addChild(charSprite);
        spaceScene.addEntity(player);
        CoreEngine.getCamera().setPlayer(player);

        //sprite.getTransform().setPosition(new Vector2f(-0.5f, 0.2f));

        String p1 = planetGen.getPlanet().getName();
        String planetName = "";

        PlayerScene terrainScene = new PlayerScene(seed);

        Terrain terr = new Terrain();
        terrainScene.addEntity(terr);
        terrainScene.addEntity(player);

        CoreEngine.setScene(terrainScene);

        while (!DisplayManager.getShouldWindowClose()) {

            CoreEngine.update();
            TextManager.render();

            if (spaceScene.isActive()) {
                if (!planetGen.getPlanet().isHovering(player.getTransform().getPosition())) {
                    planetName = "";
                } else {
                    planetName = p1;
                }
            }

            text.updateText("Planet: " + planetName);

            DisplayManager.loop();
        }

        DisplayManager.closeDisplay();

    }
}
