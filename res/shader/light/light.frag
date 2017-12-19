#version 130

in vec2 pass_texCoords;

out vec4 outColor;

uniform vec4 lightColor;
uniform vec2 resolution;
vec2 center = vec2(0, 0);

vec2 pixel;

void main() {
    vec2 pixel=pass_texCoords;
    pixel.y=resolution.y-pixel.y;
    vec2 aux=center-pixel;
    float distance=length(pass_texCoords);

	vec4 color1 = vec4(lightColor.rgb, lightColor.a * (1.0 - distance));

	outColor = color1;

}