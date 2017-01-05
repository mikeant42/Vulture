#version 130

in vec2 pass_texCoords;


out vec4 outColor;

uniform sampler2D spriteTex;

void main() {
	vec4 color1 = texture(spriteTex, pass_texCoords);

	float gray = dot(color1.rgb, vec3(0.299, 0.587, 0.114));
	vec3 grayScale = vec3(gray, gray, gray);

	// Make the thing a circle
	float dx = pass_texCoords.x;
	float dy = pass_texCoords.y;

	if ((dx*dx) + (dy*dy) > 1) {
	    outColor = vec4(0,0,0,0);
	} else {
	    outColor = vec4(gray, gray, gray, color1.a);
	}


}