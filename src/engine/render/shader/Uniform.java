package engine.render.shader;

public class Uniform {
    private String name;
    private Object value;

    public Uniform(String name, Object value) {
        this.name = name;
        if (isValidUniform(value)) {
            this.value = value;
        } else {
            System.err.println("ALERT!!!! Value not valid uniform");
        }
    }

    private boolean isValidUniform(Object object) {
        boolean isValid = true;
        for (Class c : RawShader.getValidUniformClasses()) {
            if (!object.getClass().equals(c)) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }
}
