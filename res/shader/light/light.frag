#version 130

in vec2 pass_texCoords;
in vec2 pass_pos;

out vec4 outColor;

uniform vec4 lightColor;
uniform vec2 resolution;
vec2 center = vec2(0, 0);

vec2 pixel;

void main() {
    //uv -= center;
    //float distance=sqrt(dot(uv, uv));;
    float distance = length(pass_pos);

	vec4 color1 = vec4(lightColor.rgb, lightColor.a * (1.0 - distance));

	outColor = color1;

}