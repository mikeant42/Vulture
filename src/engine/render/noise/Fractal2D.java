package engine.render.noise;

/**
 * Created by anarchist on 1/4/17.
 */
public class Fractal2D implements Function2D {

    private Function2D source;
    private int octaves;
    private float fade;

    public Fractal2D(Function2D source, int octaves, float fade) {
        this.source = source;
        this.octaves = octaves;
        this.fade = fade;
    }

    public float eval(float x, float y) {
        float sum = 0;
        float curWeight = 1.0f;
        for(int i = 0; i < octaves;i++){
// The bit shifts are just a way to quickly get the i'th power of 2.
            sum += curWeight*source.eval(x*(1<<i),y*(1<<i));
            curWeight *= fade;
        }
        return sum;
    }
}
