package engine.algo;

import engine.util.JUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarchist on 2/15/2017.
 */
public class PlanetNameGenerator {
    public static String generate(int seed) {
        List<String> beginning = new ArrayList<>();
        List<String> middle = new ArrayList<>();
        List<String> end = new ArrayList<>();

        beginning = JUtil.loadFileToArray("res/name/planetbeg.txt");

        System.out.println(beginning.get(5));
        return "";
    }
}
