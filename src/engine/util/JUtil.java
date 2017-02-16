package engine.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarchist on 2/15/2017.
 */
public class JUtil {
    public static List<String> loadFileToArray(String fileName) {
        BufferedReader abc = null;
        List<String> arr = new ArrayList<>();
        try {
            abc = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        try {
            while((line = abc.readLine()) != null) {
                arr.add(line);
                //System.out.println(data);
            }
            abc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }
}
