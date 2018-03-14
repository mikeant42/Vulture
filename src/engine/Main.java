package engine;

import engine.base.CoreEngine;
import engine.base.Seed;
import engine.input.CursorHandler;
import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import engine.math.Vector2i;
import engine.math.Vector4f;
import engine.physics.GroundPhysicsEngine;
import engine.player.ControlledPlayer;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.player.RoamerPlayer;
import engine.render.DisplayManager;
import engine.render.Light;
import engine.render.gui.font.FontType;
import engine.render.gui.font.GUIText;
import engine.render.gui.font.TextManager;
import engine.render.space.SpaceScene;
import engine.render.space.planet.Planet;
import engine.render.texture.Texture;
import engine.terrain.TerrainScene;
import engine.terrain.TiledTerrain;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;

/**
 * Created by anarchist on 8/28/16.
 */
public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay("Vulture Engine");

        Seed seed = new Seed();
        seed.seed = 52;


        CoreEngine.init();

        TextManager.init(CoreEngine.getLoader());

        Texture spriteTex = Texture.loadTexture("blackmage_m.png");
        spriteTex.setNumberOfRows(4);

        ControlledPlayer player = new ControlledPlayer(spriteTex);

        player.setRunBack(new Vector2i(1,4));
        player.setRunLeft(new Vector2i(5, 8));
        player.setRunRight(new Vector2i(9, 12));
        player.setRunForward(new Vector2i(13, 16));

        player.getTransform().setScale(0.1f);
        player.getTransform().setPosition(new Vector2f(0,1.5f));

        SpaceScene spaceScene = new SpaceScene(seed);
        //spaceScene.create();



        FontType font = new FontType(Texture.loadTexture("font/BadMofo.png").getTextureID(), new File("res/tex/font/BadMofo.fnt"));

        GUIText text = new GUIText("Planet: ", 1.5f, font, new Vector2f(0.03f,0.92f), 1f, false);
        text.setColor(new Vector3f(1,1,1));

        TextManager.loadText(text);

        //sprite.addChild(charSprite);
        spaceScene.setControlledPlayer(player);
        CoreEngine.getCamera().setPlayer(player);

        //sprite.getTransform().setPosition(new Vector2f(-0.5f, 0.2f));

        //String p1 = planetGen.getPlanet().getName();
        String planetName = "helloworld";

        TerrainScene terrainScene = new TerrainScene(seed);


        TiledTerrain terrain = new TiledTerrain();
        terrain.addToScene(terrainScene);
        terrainScene.setControlledPlayer(player);

        Texture bunnyTex = Texture.loadTexture("orangehair.png");
        bunnyTex.setNumberOfRows(4);
        RoamerPlayer bunny  = new RoamerPlayer(bunnyTex);
        bunny.setNodeName("bunny");

        bunny.setRunBack(new Vector2i(1,4));
        bunny.setRunLeft(new Vector2i(5, 8));
        bunny.setRunRight(new Vector2i(9, 12));
        bunny.setRunForward(new Vector2i(13, 16));

        bunny.getTransform().setScale(0.1f);
        bunny.getTransform().setPosition(new Vector2f(0,0));
//        bunny.setRunForward(new Vector2i(1,3));
//        bunny.setRunLeft(new Vector2i(4,6));
//        bunny.setRunRight(new Vector2i(7,9));
//        bunny.setRunBack(new Vector2i(10,12));



        terrainScene.addEntity(bunny);

        GroundPhysicsEngine physicsEngine = new GroundPhysicsEngine(player, terrain);

        terrainScene.setPhysicsEngine(physicsEngine);
        CoreEngine.setScene(terrainScene);


        //CoreEngine.setScene(spaceScene);
        //spaceScene.setPhysicsEngine(physicsEngine);


        while (!DisplayManager.getShouldWindowClose()) {

            CoreEngine.update();
            TextManager.render();

            if (spaceScene.isActive()) {
                Planet mp = spaceScene.getPlanetHovering(player.getTransform().getPosition());
                if (mp != null) {
                    planetName = mp.getName();
                    System.out.println(planetName);
                    if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_L)) {
                        CoreEngine.setScene(terrainScene);
                        terrainScene.setPhysicsEngine(physicsEngine);
                    }
                }
            }

            if (terrainScene.isActive()) {
                if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_Q)) {
                    CoreEngine.setScene(spaceScene);
                    spaceScene.setPhysicsEngine(physicsEngine);
                }
            }

            text.updateText("Planet: " + planetName);

            DisplayManager.loop();
        }

        DisplayManager.closeDisplay();

    }
}
