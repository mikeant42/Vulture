#version 130

precision highp float;

vec2 pass_texCoords = gl_FragCoord.xy;

out vec4 outColor;

const int NUM_METABALLS = 50;

uniform vec3 metaBalls[NUM_METABALLS];
const float WIDTH = 10;
const float HEIGHT = 10;

void main(){
    float x  = pass_texCoords.x;
    float y = pass_texCoords.y;
    for (int i = 0; i <  + NUM_METABALLS; i++) {
        vec3 mb = metaBalls[i];
        float dx = mb.x - x;
        float dy = mb.y - y;
        float r = mb.z;
        if (dx*dx + dy*dy < r*r) {
            outColor = vec4(x/WIDTH, y/HEIGHT,
                                0.0, 1.0);
            return;
        }
    }
   outColor = vec4(0.0, 0.0, 0.0, 0.0);
}
