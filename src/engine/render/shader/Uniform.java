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
            this.value = 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
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
