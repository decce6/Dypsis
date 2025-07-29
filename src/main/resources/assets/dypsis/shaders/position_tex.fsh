#version 330
out vec4 FragColor;

in vec4 fColor;
in vec2 fUV;

uniform sampler2D fTexture;

void main()
{
    FragColor = texture(fTexture, fUV) * fColor;
}