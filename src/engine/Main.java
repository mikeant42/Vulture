package engine;

import engine.base.CoreEngine;
import engine.player.Player;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.player.Starship;
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


        CoreEngine.init();

        TextManager.init(CoreEngine.getLoader());

        Texture spriteTex = Texture.loadTexture("blackmage_m.png");
        spriteTex.setNumberOfRows(4);
        Starship player = new Starship(spriteTex);
        player.getTransform().setScale(0.1f);

        PlanetGenerator planetGen = new PlanetGenerator(PlanetGenerator.PLANET_TYPE_HABIT);


        Space thing = new Space();
        thing.getTransform().setScale(5f);
        thing.getTransform().setPosition(new Vector2f(0,0));
        CoreEngine.addEntity(thing);

        CoreEngine.addEntity(planetGen.getPlanet());

        FontType font = new FontType(Texture.loadTexture("font/BadMofo.png").getTextureID(), new File("res/tex/font/BadMofo.fnt"));

        GUIText text = new GUIText("Planet: ", 1.5f, font, new Vector2f(0.03f,0.92f), 1f, false);
        text.setColor(new Vector3f(1,1,1));

        TextManager.loadText(text);

        //sprite.addChild(charSprite);
        CoreEngine.addEntity(player);
        CoreEngine.getCamera().setPlayer(player);

        //sprite.getTransform().setPosition(new Vector2f(-0.5f, 0.2f));

        String planetName = "";

        Terrain terr = new Terrain();
        CoreEngine.addEntity(terr);


        while (!DisplayManager.getShouldWindowClose()) {

            CoreEngine.update();
            TextManager.render();

            if (planetGen.getPlanet().isHovering(player.getTransform().getPosition())) {
                planetName = planetGen.getPlanet().getName();
            } else {
                planetName = "";
            }

            text.updateText("Planet: " + planetName);

            DisplayManager.loop();
        }

        DisplayManager.closeDisplay();

    }
}
