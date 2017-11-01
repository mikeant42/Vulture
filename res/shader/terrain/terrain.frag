#version 130

in vec2 pass_texCoords;


out vec4 outColor;

uniform sampler2D spriteTex;

void main() {
	vec4 color1 = texture(spriteTex, pass_texCoords);

	outColor = color1;

}