#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform vec3 attenuation[4];
uniform vec3 lightColour[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void) {
    //AMBIENT AND DIFFUSE LIGHTING
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToCameraVector = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i=0; i<4; i++) {
        float distance = length(toLightVector[i]);
        float attenuationFactor = 1.0/(attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance));

        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot = dot(unitNormal,unitLightVector);
        float brightness = max(nDot,0.0);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
        float specularFactor = dot(unitToCameraVector,reflectedLightDirection);
        specularFactor = max(specularFactor,0.0);
        float dampedFactor = pow(specularFactor,shineDamper);

        totalDiffuse += brightness * lightColour[i] * attenuationFactor;
        totalSpecular += dampedFactor * reflectivity * lightColour[i] * attenuationFactor;
    }

    totalDiffuse = max(totalDiffuse,0.2);


    vec4 textureColour = texture(textureSampler,pass_textureCoords);
    if(textureColour.a<0.5){
        discard;
    }

    out_Colour = vec4(totalDiffuse,1.0) * textureColour + vec4(totalSpecular,1.0);
    out_Colour = mix(vec4(skyColour,1.0),out_Colour,visibility);
}