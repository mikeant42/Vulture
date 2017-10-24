#version 130

in vec2 pass_texCoords;


out vec4 outColor;

uniform sampler2D noiseSample;
uniform sampler2D cloudSample;

uniform float radius;
uniform vec2 center;
uniform float atmosphereBorder;

uniform vec2 resolution;
uniform float time;


uniform vec4 atmosphereColor;

vec2 lightPos = vec2(0.2, 0.2);
vec3 DIFFUSE_LIGHT = vec3(1,1,1);
vec3 AMBIENT_LIGHT = vec3(0.5,0.5,0.5);

//Cloud SETTINGS - these all need to be uniforms//
const float timeScale = 0.8;
const float cloudScale = 0.005;
const float skyCover = 0.04;
const float softness = 0.2;
const float brightness = 1.0;
const int noiseOctaves = 8;
const float curlStrain = 3.0;
//SETTINGS//

#uniforminclude

float saturate(float num)
{
    return clamp(num,0.0,1.0);
}

float noise(vec2 uv)
{
    return texture(cloudSample,uv).r;
}

vec2 rotate(vec2 uv)
{
    uv = uv + noise(uv*0.2)*0.005;
    float rot = curlStrain;
    float sinRot=sin(rot);
    float cosRot=cos(rot);
    mat2 rotMat = mat2(cosRot,-sinRot,sinRot,cosRot);
    return uv * rotMat;
}

float fbm (vec2 uv)
{
    float rot = 1.57;
    float sinRot=sin(rot);
    float cosRot=cos(rot);
    float f = 0.0;
    float total = 0.0;
    float mul = 0.5;
    mat2 rotMat = mat2(cosRot,-sinRot,sinRot,cosRot);

    for(int i = 0;i < noiseOctaves;i++)
    {
        f += noise(uv+time*0.00015*timeScale*(1.0-mul))*mul;
        total += mul;
        uv *= 3.0;
        uv=rotate(uv);
        mul *= 0.5;
    }
    return f/total;
}

vec4 clouds(vec4 bottom) {
	vec2 screenUv = pass_texCoords.xy/resolution.xy;
    vec2 uv = pass_texCoords.xy/(40000.0*cloudScale);

    float cover = skyCover;

    float bright = brightness*(1.8-cover);

    float color1 = fbm(uv-0.5+time*0.00004*timeScale);
    float color2 = fbm(uv-10.5+time*0.00002*timeScale);

    float clouds1 = smoothstep(1-cover,min((1.0-cover)+softness*2.0,1.0),color1);
    float clouds2 = smoothstep(1-cover,min((1.0-cover)+softness,1.0),color2);

    float cloudsFormComb = saturate(clouds1+clouds2);

    //vec4 skyCol = vec4(0.6,0.8,1.0,1.0);
    vec4 skyCol = bottom;
    float cloudCol = saturate(saturate(1.0-pow(color1,1.0)*0.2)*bright);
    vec4 clouds1Color = vec4(cloudCol,cloudCol,cloudCol,1.0);
    vec4 clouds2Color = mix(clouds1Color,skyCol,0.25);
    vec4 cloudColComb = mix(clouds1Color,clouds2Color,saturate(clouds2-clouds1));

	return mix(skyCol,cloudColComb,cloudsFormComb);
}

vec4 toGrayscale(vec4 color) {
  float average = (color.r + color.g + color.b) / 3.0;
  return vec4(average, average, average, 1.0);
}

vec4 mixman(float x, float y, vec4 a) {
    return x * (1 - a) + y * a; // literally same thing as mix()
}

void main() {
	vec4 color1 = texture(noiseSample, (pass_texCoords * vec2(1.0, -1.0)));
	//vec4 grayScale = toGrayscale(color1); // make sure we get the grayscale image
	vec4 grayScale = toGrayscale(color1);
	//vec4 colorTex = texture(colorSample, grayScale.zw * vec2(1.0, -1.0));

    // The function colormap(float) will always come from #uniforminclude
	vec4 colorTex = colormap(grayScale.x) * grayScale;

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

    //ultColor = mix(atmosphereColor, ultColor ,t);

    vec4 trr = clouds(ultColor);

    ultColor = mix(atmosphereColor, trr, t);

	// Make the thing a circle
	//----------------------------------------------
    if ( (dist < (radius)) ) {
        outColor = ultColor;
    } else {
        outColor = vec4(0);
    }


    //----------------------------------------------

}