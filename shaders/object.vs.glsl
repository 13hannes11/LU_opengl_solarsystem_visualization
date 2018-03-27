#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texturePos;
layout(location = 2) in vec3 normalVec;

out vec2 fragTexCoord;
out vec3 fragNormal;
out vec3 fragPos;

uniform mat4 ModelMatrix;
uniform mat4 CameraMatrix;

void main() {
	gl_Position = CameraMatrix * ModelMatrix *  vec4(position, 1.0);

	fragTexCoord = texturePos;
	fragNormal = normalVec;
	fragPos = vec3(ModelMatrix * vec4(position, 1));
}
