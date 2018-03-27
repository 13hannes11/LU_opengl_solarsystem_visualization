#version 330

in vec3 texCoord;

out vec4 outputColor;

uniform samplerCube cubemap;

void main()
{
    outputColor = texture(cubemap, texCoord);
}
