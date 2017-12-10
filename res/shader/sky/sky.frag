#version 130


uniform float time;
uniform vec2 resolution;

in vec2 pass_texCoords;

out vec4 outColor;

void main(void) {

    const vec4 color0 = vec4(0.0f, 0.0f, 1.0f, 1.0f);
    const vec4 color1 = vec4(0.3294f, 0.92157f, 1.0f, 1.0f);
    outColor = mix(color0, color1, pass_texCoords.x + pass_texCoords.y - 2 * pass_texCoords.x * pass_texCoords.y);;
    //outColor = vec4(color0 * (pass_texCoords.y / resolution.y), 1) + vec4(color1 * (1.4 - (pass_texCoords.y / resolution.y)), 1);

}
