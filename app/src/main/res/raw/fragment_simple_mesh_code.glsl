#version 300 es
precision mediump float;

in vec3 mColor;

out vec4 oColor;

void main(){

    oColor = vec4(mColor, 1);
}