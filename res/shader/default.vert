#version 130

in vec2 position;

out vec2 pass_texCoords;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;


void main(){
	gl_Position = viewMatrix * transformationMatrix * vec4(position, 0.0, 1.0);
	pass_texCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);

}
