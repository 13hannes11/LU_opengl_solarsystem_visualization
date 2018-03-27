#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 texturePos;

out vec3 texCoord;

uniform mat4 CameraMatrix;
uniform mat4 ModelMatrix;

void main() {
    gl_Position = CameraMatrix *  ModelMatrix  *  vec4(position, 1.0);
    texCoord = vec3(texturePos.x, -texturePos.y, texturePos.z);
}
