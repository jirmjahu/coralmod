#version 150

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec2 UV0;
in vec2 UV1;
in float Radius;
in vec4 Color;

out vec2 texCoord0;
out vec2 widthHeight;
out float radius;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    texCoord0 = UV0;
    widthHeight = UV1;
    radius = Radius;
    vertexColor = Color;
}