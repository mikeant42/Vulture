package engine.algo;

import engine.base.Seed;
import engine.util.JUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by anarchist on 2/15/2017.
 */
public class PlanetNameGenerator {
    public static String generate(Seed seed) {
        List<String> beginning;

        beginning = JUtil.loadFileToArray("res/name/planetbeg.txt");

        Random s = new Random();
        s.setSeed(seed.seed);

        int firstPartSize = 3;
        String firstPart = beginning.get(s.nextInt(beginning.size()));
        firstPart = firstPart.substring(0, Math.min(firstPart.length(), firstPartSize));

        int lastPartSize = 7;
        String lastPart = beginning.get(s.nextInt(beginning.size()));
        lastPart = lastPart.substring(firstPartSize, Math.min(lastPart.length(), firstPartSize + lastPartSize));

        return firstPart + lastPart;
    }
}
