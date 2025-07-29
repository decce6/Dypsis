uniform mat4 modelView;
uniform mat4 projection;
// The color set by glColor4f etc.
// Range: [0, 1]
uniform vec4 globalColor;

vec4 convert_color(vec4 color) {
    return color / 255;
}