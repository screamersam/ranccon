#version 300 es
precision mediump float;

in vec2 oTexturePosition;
in vec3 surfaceNormal;
in vec3 vectorToLight; //vtl
in vec3 vectorToCamera; //vtc

out vec4 oColor;

uniform sampler2D mSampler;
uniform vec3 uLightC;
uniform float uShineDumper;
uniform float uReflectivity;


void main(){

    vec3 nNormal = normalize(surfaceNormal);
    vec3 nVTL = normalize(vectorToLight);

    //calculo do angulo da luz sobre a superficie
    float fLuz = dot(nNormal, nVTL);
    float brilho = max(fLuz, 0.2);
    vec3 diffuse = brilho * uLightC;

    vec3 nVTC = normalize(vectorToCamera);
    vec3 direcaoDaLuz = -nVTL;
    vec3 direcaoMaxReflecao = reflect(direcaoDaLuz, nNormal);

    float fatorEspecular = max(dot(direcaoMaxReflecao, nVTC), 0.0);
    float fatorDumper = pow(fatorEspecular, uShineDumper);
    vec3 especular = fatorDumper * uReflectivity * uLightC;

    oColor = vec4(diffuse, 1.0) * texture(mSampler, oTexturePosition) + vec4(especular, 1);
}