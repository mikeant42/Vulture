package engine.render.space.planet;

import engine.algo.PlanetNameGenerator;
import engine.algo.noise.Fractal2D;
import engine.algo.noise.Function2D;
import engine.algo.noise.SimplexNoise;
import engine.base.Seed;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.util.MathUtil;

/**
 * Created by anarchist on 1/18/17.
 */
public class PlanetGenerator {
    public static final int PLANET_TYPE_HABIT = 0;
    public static final int PLANET_TYPE_GAS   = 1;


    private Function2D function;
    private String colorMap;

    private Planet planet;

    // The seed will come from the universe generator
    // This is the seed specific to the planet
    private Seed seed;

    public PlanetGenerator(int type, Seed seed) {
        this.seed = seed;

        if (type == PLANET_TYPE_GAS) {
            planet = new GasPlanet();

            this.function = new Fractal2D(new SimplexNoise(this.seed.seed), 2, 2f);
            this.colorMap = "colormap/IDL_Prism.frag";

            planet.generate(colorMap, function);
            planet.getTransform().setScale(1.75f);
            planet.setName(PlanetNameGenerator.generate(seed));
            planet.setAtmosphereColor(planet.genRandomAtmoColor(seed));

        } else {
            // Do this another time
            planet = new HabitablePlanet();

            this.function = new Fractal2D(new SimplexNoise(this.seed.seed), 6, 0.85f);
            this.colorMap = "colormap/IDL_CB-BuGn.frag";
            //this.colorMap = "colormap/MATLAB_autumn.frag";

            planet.generate(colorMap, function);
            planet.getTransform().setScale(1.75f);
            planet.setName(PlanetNameGenerator.generate(seed));
        }


    }

    public Planet getPlanet() {
        return planet;
    }


    public static Seed generatePlanetSeed(Seed galaxySeed, Vector2f planetPosition) {
        Seed seed = new Seed();

        int a = MathUtil.psrandomInt(galaxySeed);
        int b = (int)(MathUtil.psrandomFloat(planetPosition.x, planetPosition.y, galaxySeed));
        int c = (int)(a * b / (planetPosition.x / 2));
        seed.seed = c;

        return seed;
    }


}
