#version 130

precision highp float;
precision highp int;

uniform float time;
in vec2 pass_texCoords;

out vec4 outColor;


float rnd(vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main()  {
    vec2 resolution = vec2(1080, 720);
    float t = time;
    vec2 vUv = pass_texCoords;
    vUv /= resolution.x;
    outColor = vec4(0.0);
    for (float i = 0.0; i < 200.0; i += 1.0)
    {
        float r = (0.5 + 0.5 * rnd(i)) * 0.003;
        vec2 point = rnd(i + vec2(1.0, 2.0));
        vec2 veloc = rnd(i + vec2(3.0, 4.0)) - 0.5;
        vec2 point_real = fract(point + veloc * t * 0.05);
        float dist = length(point_real - vUv) / r;
        if (dist < 1.0)
        {
            outColor += 1.0 - dist;
        }
     }
}