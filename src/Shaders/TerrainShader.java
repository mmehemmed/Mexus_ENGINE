package Shaders;

import Entities.Camera;
import Entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import toolBox.Maths;

import java.util.Comparator;
import java.util.List;

public class TerrainShader extends ShaderProgram{
    private static final String VERTEX_FILE = "src/Shaders/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/Shaders/terrainFragmentShader.glsl";

    private static final int MAX_LIGHTS = 4;

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;

    private int location_lightPosition[];
    private int location_lightColour[];


    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
        location_blendMap = super.getUniformLocation("blendMap");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1,"textureCoords");
        super.bindAttribute(2,"normal");
    }
    public void connectTextureUnits(){
        super.loadInteger(location_backgroundTexture,0);
        super.loadInteger(location_rTexture,1);
        super.loadInteger(location_gTexture,2);
        super.loadInteger(location_bTexture,3);
        super.loadInteger(location_blendMap,4);
    }
    public void loadSkyColour(float r,float g,float b){
        super.loadVector3f(location_skyColour,new Vector3f(r,g,b));
    }
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }

    public void loadLights(List<Light> lights) {
        for (int j = 0; j < MAX_LIGHTS; j++) {
            if (j < lights.size()) {
                super.loadVector3f(location_lightPosition[j], lights.get(j).getPosition());
                super.loadVector3f(location_lightColour[j], lights.get(j).getColour());
            } else {
                super.loadVector3f(location_lightPosition[j], new Vector3f(0, 0, 0));
                super.loadVector3f(location_lightColour[j], new Vector3f(0, 0, 0));
            }
        }
    }
    public void loadShineVariables(float shineDamper,float reflectivity){
        super.loadFloat(location_shineDamper,shineDamper);
        super.loadFloat(location_reflectivity,reflectivity);
    }
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix,projection);
    }
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix,viewMatrix);
    }


}
