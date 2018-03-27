#version 400
struct LightSource {
	//int type; so far only point lights supported
	int enabled;
	vec3 position;
};
struct Material {
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	int shininess;
};



in vec2 fragTexCoord;
in vec3 fragNormal;
in vec3 fragPos;

out vec4 outputColor;

uniform mat4 ModelMatrix;
uniform sampler2D textureSampler;
uniform Material material;
uniform vec3 EyePos;

const int MaxLigths = 10;
uniform LightSource lights[MaxLigths];




void main()
{
    //calculate normal in world coordinates
    mat3 normalMatrix = transpose(inverse(mat3(ModelMatrix)));
    vec3 normal = normalize(normalMatrix * fragNormal);
    vec3 eyeDir = normalize(EyePos - fragPos);

    for(int i = 0; i < MaxLigths; ++i) {
		//calculate the vector from this pixels surface to the light source
		vec3 lightDir = lights[i].position - fragPos;

		float attenuation = 1.0 / length(lightDir);
		lightDir = normalize(lightDir);


		//Diffuse
		float diffuseIntensity = max(0.0, dot(normal, lightDir));
		vec3 diffuseLight = attenuation *  material.diffuse * diffuseIntensity;

		//Specular
		vec3 reflection = 2 * dot(lightDir, normal) * normal - lightDir;
		float specularIntensity = max(0.0, dot(reflection, eyeDir));
		specularIntensity = pow(specularIntensity, material.shininess);

		vec3 specularLight = attenuation *  specularIntensity * material.specular;


		vec4 color = texture(textureSampler, fragTexCoord);
		outputColor += lights[i].enabled * color * vec4(material.ambient + diffuseLight, 1.0) + specularLight;
    }
}
