#version 330
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aUV;

#include "shared.glsl"

out vec4 fColor;
out vec2 fUV;

void main()
{
    gl_Position = projection * modelView * vec4(aPos, 1.0);
    fColor = globalColor;
    fUV = aUV;
}