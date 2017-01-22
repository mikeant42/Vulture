package engine.util;

/**
 * Created by anarchist on 8/28/16.
 */
public class ResourceManager {
    public static final String SHADER_INCLUDE_DIRECTIVE = "#include";
    public static final String SHADER_UNIFORM_INCLUDE = "#uniforminclude";

    private static final String SHADER_INCLUDE_PATH = "res/shader/";
    private static final String MODEL_INCLUDE_PATH = "res/models/";
    private static final String TEXTURE_INCLUDE_PATH = "res/tex/";
    private static final String FONT_RES_PATH = "res/font";
    private static final String PIC_RES_PATH = "pics/";

    public static String LoadPath(String string) {
        return string;
    }

    public static String LoadShaderPath(String path) {
        return LoadPath(SHADER_INCLUDE_PATH + path);
    }

    public static String LoadModelPath(String path) {
        return LoadPath(MODEL_INCLUDE_PATH + path);
    }

    public static String LoadTexturePath(String path) {
        return LoadPath(TEXTURE_INCLUDE_PATH + path);
    }

    public static String LoadPicturePath(String path) {
        return LoadPath(PIC_RES_PATH + path);
    }

    public static String LoadFontPath(String path) {
        return LoadPath(FONT_RES_PATH + path);
    }

}
