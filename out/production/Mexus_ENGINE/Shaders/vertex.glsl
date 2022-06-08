#version 400 core

in vec3 posistion;
in vec2 textureCoords;

out vec3 colour;
out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

void main(void){
    gl_Position = projectionMatrix * transformationMatrix * vec4(posistion, 1.0);
    pass_textureCoords = textureCoords;
    colour = vec3(posistion.x+0.5,0,posistion.z +0.5);
}