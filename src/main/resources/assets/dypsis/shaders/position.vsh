#version 330
layout (location = 0) in vec3 aPos;

#include "shared.glsl"

out vec4 fColor;

void main()
{
    gl_Position = projection * modelView * vec4(aPos, 1.0);
    fColor = globalColor;
}