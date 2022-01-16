#version 300 es

in vec2 aPosition;
in vec3 aColor;

out vec3 mColor;

uniform mat4 uMatrix;
uniform mat4 uProjection;
uniform mat4 uViewMatrix;

void main()
{
    vec4 worldPosition = uMatrix * vec4(aPosition, 0, 1.0);
    gl_Position =  uViewMatrix * uProjection * worldPosition;
    mColor = aColor;
}