package engine;

import engine.base.CoreEngine;
import engine.base.PlayerScene;
import engine.base.Seed;
import engine.input.KeyboardHandler;
import engine.math.Vector4f;
import engine.physics.GroundPhysicsEngine;
import engine.player.Player;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.render.DisplayManager;
import engine.render.Light;
import engine.render.ground.Sky;
import engine.render.gui.font.FontType;
import engine.render.gui.font.GUIText;
import engine.render.gui.font.TextManager;
import engine.render.space.Space;
import engine.render.space.SpaceScene;
import engine.render.space.planet.Planet;
import engine.render.space.planet.PlanetGenerator;
import engine.render.texture.Texture;
import engine.terrain.Terrain;
import engine.terrain.TerrainScene;
import engine.terrain.TiledTerrain;
import engine.util.MathUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
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
        Player player = new Player(spriteTex);
        player.getTransform().setScale(0.1f);
        player.getTransform().setPosition(new Vector2f(0,1.5f));

        SpaceScene spaceScene = new SpaceScene(seed);



        FontType font = new FontType(Texture.loadTexture("font/BadMofo.png").getTextureID(), new File("res/tex/font/BadMofo.fnt"));

        GUIText text = new GUIText("Planet: ", 1.5f, font, new Vector2f(0.03f,0.92f), 1f, false);
        text.setColor(new Vector3f(1,1,1));

        TextManager.loadText(text);

        //sprite.addChild(charSprite);
        spaceScene.addEntity(player);
        CoreEngine.getCamera().setPlayer(player);

        //sprite.getTransform().setPosition(new Vector2f(-0.5f, 0.2f));

        //String p1 = planetGen.getPlanet().getName();
        String planetName = "helloworld";

        TerrainScene terrainScene = new TerrainScene(seed);
        Sky sky = new Sky();
        sky.getTransform().setScale(5f);
        sky.getTransform().setPosition(new Vector2f(0,0));
        terrainScene.addEntity(sky);


        TiledTerrain terrain = new TiledTerrain();
        terrain.addToScene(terrainScene);
        terrainScene.addEntity(player);

        Light light = new Light(new Vector4f(0.7f, 0.2f, 0.2f, 0.8f));
        terrainScene.addEntity(light);


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
