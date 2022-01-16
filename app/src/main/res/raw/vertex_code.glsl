#version 300 es

in vec3 position;
in vec2 texturePosition;
in vec3 normal;

uniform mat4 uMatrix;
uniform mat4 uProjection;
uniform mat4 uViewMatrix;
uniform vec3 uLightP;

out vec2 oTexturePosition;
out vec3 surfaceNormal;
out vec3 vectorToLight;
out vec3 vectorToCamera;

void main()
{
    vec4 worldPosition = uMatrix * vec4(position, 1.0);
    gl_Position =  uViewMatrix * uProjection * worldPosition;
    oTexturePosition = texturePosition;

    surfaceNormal = (uMatrix * vec4(normal, 0.0)).xyz;
    vectorToLight = uLightP - worldPosition.xyz;
    vectorToCamera = (inverse(uViewMatrix) * vec4(0, 0, 0, 1)).xyz - worldPosition.xyz;

}