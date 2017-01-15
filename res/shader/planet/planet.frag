#version 130

in vec2 pass_texCoords;


out vec4 outColor;

uniform sampler2D spriteTex;
uniform float radius;
uniform vec2 center;
uniform float border;

void main() {
	vec4 color1 = texture(spriteTex, pass_texCoords * vec2(1.0, -1.0));

	float gray = dot(color1.rgb, vec3(0.299, 0.587, 0.114));
	vec3 grayScale = vec3(gray, gray, gray); // make sure we get the grayscale image



	// Make the thing a circle
	//----------------------------------------------
	// Offset uv with the center of the circle.
    vec2 uv = center - pass_texCoords;

    float dist =  sqrt(dot(uv, uv));
    if ( (dist < (radius)) ) {
        outColor = vec4(grayScale, 1);
    } else {
        outColor = vec4(0);
    }

    //----------------------------------------------


}