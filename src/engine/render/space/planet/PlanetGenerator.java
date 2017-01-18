package engine.render.space.planet;

import engine.render.noise.Fractal2D;
import engine.render.noise.Function2D;
import engine.render.noise.SimplexNoise;
import engine.render.noise.VoronoiNoise;

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
    private int seed = 10;

    public PlanetGenerator(int type) {
        if (type == PLANET_TYPE_GAS) {
            planet = new GasPlanet();

            this.function = new Fractal2D(new SimplexNoise(this.seed), 6, 0.8f);
            this.colorMap = "colormap/IDL_Eos_A.frag";

            planet.generate(colorMap, function);
            planet.getTransform().setScale(0.7f);
        } else {
            planet = new HabitablePlanet();

            this.function = new Fractal2D(new SimplexNoise(this.seed), 6, 0.8f);
            this.colorMap = "colormap/IDL_CB-BuGn.frag";

            planet.generate(colorMap, function);
            planet.getTransform().setScale(0.7f);
        }


    }

    public Planet getPlanet() {
        return planet;
    }
}
