#version 130

in vec2 pass_texCoords;

out vec4 outColor;

float radius = 0.7;
uniform vec2 center;

uniform float time;
uniform vec2 resolution;

#define BLU_SPEED	0.4
#define RED_SPEED	0.8

float border = 0.08;

// Lightning shader
// rand,noise,fmb functions from https://www.shadertoy.com/view/Xsl3zN
// jerome

vec4 wire() {
    vec4 ret = vec4(0);

    vec2 pos=(pass_texCoords.xy/resolution.y);
	pos.x-=resolution.x/resolution.y/2.0;pos.y-=0.5;
	vec2 mse = vec2(0.0); //the only good variable is an initialized variable
	mse.x+=resolution.x/resolution.y;

	float fx=sin(pos.x*10.0+time*BLU_SPEED+mse.x*BLU_SPEED*2.0)/4.0;
	float dist=abs(pos.y-fx)*80.0;
	ret+=vec4(0.5/dist,0.5/dist,1.0/dist,1.0);

	fx=cos(pos.x*10.0+time*RED_SPEED+mse.x*RED_SPEED*2.0)/4.0;
	dist=abs(pos.y-fx)*80.0;
	ret+=vec4(1.0/dist,0.5/dist,0.5/dist,1.0);

	if (pos.y<=-0.2) {
		ret+=vec4(abs(pos.y+0.2),abs(pos.y+0.2),abs(pos.y+0.2),1.0);
	}

	return ret;
}

void main() {
    vec4 p1Color = vec4(0.5, 0.3, 0.2, 1);
    vec4 p1 = vec4(0);

    vec4 p2Color = vec4(0.3, 0.5, 0.2, 1);
    vec4 p2 = vec4(0);

    vec4 windowColor = vec4(0.9, 0.9, 0.9, 0.5);
    vec4 window = vec4(0);

    vec4 lightning = vec4(0);

    // Offset uv with the center of the circle.
    vec2 uv = vec2(0.2, 0.2) - pass_texCoords;

    // Get the distance from the centerg
    float dist =  sqrt(dot(uv, uv));

    float dx = pass_texCoords.x;
    float dy = pass_texCoords.y;

    if ( (dx*dx + dy*dy > 1) ) {
        p1 = p1Color;
    } else {
        p1 = lightning;
    }

    if ( (dx*dx + dy*dy < 0.5f) ) {
        if (dist < 0.1) {
            window = windowColor;
        } else {
            p2 = p2Color;
            window = p2Color;
        }
    } else {
        p2 = lightning;
    }

    outColor = p1 + p2 + window;
    //outColor = wire();

}