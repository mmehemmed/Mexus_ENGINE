#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_Colour;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void) {

    vec4 blendMapColour = texture(blendMap,pass_textureCoords);
    float backTextureAmount = 1.0 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
    vec2 tiledCoords = pass_textureCoords * 40.0;
    vec4 backTextureColour = texture(backgroundTexture,tiledCoords) * backTextureAmount;
    vec4 redTextureColour = texture(rTexture,tiledCoords)*blendMapColour.r;
    vec4 greenTextureColour = texture(gTexture,tiledCoords)*blendMapColour.g;
    vec4 blueTextureColour = texture(bTexture,tiledCoords)*blendMapColour.b;

    vec4 totalColour = backTextureColour + redTextureColour + greenTextureColour + blueTextureColour;

    //AMBIENT AND DIFFUSE LIGHTING
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);


    float nDot = dot(unitNormal,unitLightVector);
    float brightness = max(nDot,0.5);
    vec3 diffuse = brightness * lightColour;

    //SPECULAR LIGHTING
    vec3 unitToCameraVector = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
    float specularFactor = dot(unitToCameraVector,reflectedLightDirection);
    specularFactor = max(specularFactor,0.0);
    float dampedFactor = pow(specularFactor,shineDamper);

    vec3 finalSpecularFactor = dampedFactor * reflectivity * lightColour;



    out_Colour = vec4(diffuse,1.0) * totalColour + vec4(finalSpecularFactor,1.0);
    out_Colour = mix(vec4(skyColour,1.0),out_Colour,visibility);
}