#version 130

in vec2 pass_texCoords;


out vec4 outColor;

uniform sampler2D noiseSample;
uniform sampler2D colorSample;

uniform float radius;
uniform vec2 center;
uniform float atmosphereBorder;


uniform vec4 atmosphereColor;

vec2 lightPos = vec2(0.2, 0.2);
vec3 DIFFUSE_LIGHT = vec3(1,1,1);
vec3 AMBIENT_LIGHT = vec3(0.5,0.5,0.5);


vec4 toGrayscale(vec4 color) {
  float average = (color.r + color.g + color.b) / 3.0;
  return vec4(average, average, average, 1.0);
}

void main() {
	vec4 color1 = texture(noiseSample, (pass_texCoords * vec2(1.0, -1.0)));
	//vec4 grayScale = toGrayscale(color1); // make sure we get the grayscale image
	vec4 grayScale = color1;
	vec4 colorTex = texture(colorSample, grayScale.zw * vec2(1.0, -1.0));

    // Add the atmo color onto the planet
	colorTex += atmosphereColor;

	vec3 light = (lightPos.x*pass_texCoords.x + lightPos.y*pass_texCoords.y) * DIFFUSE_LIGHT + AMBIENT_LIGHT;


	vec4 ultColor = colorTex * vec4(light, 1);

	// Offset uv with the center of the circle.
    vec2 uv = center - pass_texCoords;

    // Get the distance from the centerg
    float dist =  sqrt(dot(uv, uv));

    // Create atmosphere / fade out
    float t = 1.0 + smoothstep(radius, radius + atmosphereBorder, dist)
                    - smoothstep(radius - atmosphereBorder, radius, dist);

    ultColor = mix(atmosphereColor, ultColor ,t);

	// Make the thing a circle
	//----------------------------------------------
    if ( (dist < (radius)) ) {
        outColor = ultColor;
    } else {
        outColor = vec4(0);
    }


    //----------------------------------------------

}