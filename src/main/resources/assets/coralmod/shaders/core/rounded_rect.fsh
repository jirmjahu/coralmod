#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:dynamictransforms.glsl>

layout(location = 0) in vec2 texCoord0;
layout(location = 1) in vec2 widthHeight;
layout(location = 2) in float radius;
layout(location = 3) in vec4 vertexColor;

layout(location = 0) out vec4 fragColor;

float roundedRect(vec2 fragCoord, vec2 halfSize, float radius) {
    vec2 offset = abs(fragCoord) - halfSize + radius;
    vec2 outsideDistance = max(offset, 0.0);

    float outsideLength = length(outsideDistance);
    float insideDistance = min(max(offset.x, offset.y), 0.0);

    return outsideLength + insideDistance - radius;
}

void main() {
    vec2 centerPos = texCoord0 - widthHeight * 0.5;
    vec2 halfSize = widthHeight * 0.5;

    float sdf = roundedRect(centerPos, halfSize, radius);

    float aa = fwidth(sdf);
    float alpha = 1.0 - smoothstep(0.0, aa, sdf);

    vec4 color = ColorModulator * vertexColor * vec4(1.0, 1.0, 1.0, alpha);

    if (color.a <= 0.001) {
        discard;
    }

    fragColor = color;
}