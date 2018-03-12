package engine.render.space;

import engine.algo.noise.Fractal2D;
import engine.algo.noise.SimplexNoise;
import engine.base.PlayerScene;
import engine.base.Seed;
import engine.math.Vector2f;
import engine.render.space.planet.Planet;
import engine.render.space.planet.PlanetGenerator;
import engine.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class SpaceScene extends PlayerScene {
    private Seed seed;
    private List<Planet> planets;
    private int numPlanets;
    private float bounds = 4;

    Fractal2D noise = new Fractal2D(new SimplexNoise(30), 2, 0.6f);

    public SpaceScene(Seed seed) {
        super(seed);
        planets = new ArrayList<>();
        this.seed = seed;


    }

    public void create() {
        generateSystem();

        Space thing = new Space();
        thing.getTransform().setScale(5f);
        thing.getTransform().setPosition(new Vector2f(0,0));
        this.addEntity(thing);

        addPlanets();
    }

    private void generateSystem() {
        numPlanets = MathUtil.psrandomInt(3, 8, seed);
        float randomBounds = MathUtil.psrandomFloat(-bounds, bounds, seed);

        //System.out.println(randomBounds);
        //System.out.println(numPlanets);

        for (int i = 0; i < numPlanets; i++) {
            float x = noise.eval(i, randomBounds) * 10;
            float y = noise.eval(x, i) * 10;

            Vector2f pos = new Vector2f(x,y);

            System.out.println("X: " + x + " Y: " + y);
//            PlanetGenerator generator = new PlanetGenerator(PlanetGenerator.PLANET_TYPE_HABIT, PlanetGenerator.generatePlanetSeed(seed, pos));
            PlanetGenerator generator = new PlanetGenerator(PlanetGenerator.PLANET_TYPE_HABIT, this.seed);

            Planet planet = generator.getPlanet();
            planet.getTransform().setPosition(pos);
            planets.add(planet);
        }

    }

    public Planet getPlanetHovering(Vector2f pos) {
        Planet returnPlanet = null;
        for (Planet planet : planets) {
            if (planet.isHovering(pos)) {
                returnPlanet = planet;
                return returnPlanet;
            }

        }

        return null;

    }

        private void addPlanets() {
            for (Planet planet : planets) {
                addEntity(planet);
            }
        }



}
