#version 130

precision highp float;

uniform float time;

uniform float starRadius;
uniform vec3 starColor;
uniform float starDensity;
uniform float speed;
uniform vec2 resolution;
uniform vec3 spaceColor;

in vec2 pass_texCoords;

out vec4 outColor;

float starrand(float seedx, float seedy, int seedp) {
    return 0.05 + 0.9 * fract(
        sin(float(seedp) * 437.234) * 374.2542 -
        cos(seedx * 432.252) * 23.643 +
        sin(seedy * 73.2454) * 372.23455
    );
}

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main(void) {

    vec2 position = pass_texCoords.xy * resolution.xy;
    float dtime = time;

    outColor = vec4(spaceColor, 1.0);

    for ( int p = 0; p < 20; p++ ) {
        float scale = ( 1.0 / starRadius ) + float( p );
        vec2 vpos = position * scale;
        vpos.x += ( dtime * speed ) / scale;
        vpos.y += speed * dtime / scale;
        vec2 spos = vec2(
            starrand(floor(vpos.x), floor(vpos.y), p),
            starrand(10.5 + floor(vpos.x), 10.5 + floor(vpos.y), p)
        );
        float px = scale / 80.0 / 3.0;
        float size = 1.0 / (scale * ( 500.0 / starDensity ) );
        float brite = 0.1;

        if( size < px ) {
            brite = size / px;
            size = px;
        }

        outColor.rgb += vec3(rand(starColor.xy), rand(starColor.xx), rand(starColor.xz)) * min(
            1.0, max(0.0, starDensity - length(spos - fract(vpos)) / size)
        ) * brite;
    }

}