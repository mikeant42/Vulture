package engine.render.gui.font;

import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.render.shader.RawShader;


/**
 * Created by anarchist on 6/19/16.
 */
public class FontShader extends RawShader {
    private static final String VERT_FILE = "gui/font/fontVertexShader.vert";
    private static final String FRAG_FILE = "gui/font/fontFragmentShader.frag";


    public FontShader() {
        super(VERT_FILE, FRAG_FILE);

        super.bindAttribute(1, "texCoords");
    }

    public void connectTextureUnits() {
       // super.bindAttribute(location_fontAtlas, 0);
    }

    protected void loadTranslation(Vector2f translation) {
        super.setUniformVec2("translation", translation);
    }

    public void loadColor(Vector3f color) {
        super.setUniformVec3("color", color);
    }

    public void loadOutlineColor(Vector3f outline) {
        super.setUniformVec3("outlineColor", outline);
    }

    public void loadBorderWidth(float width) {
        super.setUniformFloat("borderWidth", width);
    }

    public void loadDropShadow(Vector2f vec) {
        super.setUniformVec2("offset", vec);
    }
}
