package engine;

import engine.base.CoreEngine;
import engine.base.Player;
import engine.math.Vector2f;
import engine.render.DisplayManager;
import engine.render.gui.font.FontType;
import engine.render.gui.font.GUIText;
import engine.render.gui.font.TextManager;
import engine.render.space.planet.Planet;
import engine.render.space.Space;
import engine.render.texture.Texture;

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

        FontType font = new FontType(Texture.loadTexture("font/roboto-thin.png").getTextureID(), new File("res/tex/font/roboto-thin.fnt"));

        GUIText text = new GUIText("Vulture Graphics", 1.5f, font, new Vector2f(0,0), 1f, true);

        TextManager.loadText(text);

        //sprite.addChild(charSprite);
        CoreEngine.addEntity(player);
        CoreEngine.getCamera().setPlayer(player);

        //sprite.getTransform().setPosition(new Vector2f(-0.5f, 0.2f));

        while (!DisplayManager.getShouldWindowClose()) {

            CoreEngine.update();
            TextManager.render();


            DisplayManager.loop();
        }

        DisplayManager.closeDisplay();

    }
}
