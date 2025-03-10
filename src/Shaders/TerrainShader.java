package Shaders;

import Entities.Camera;
import Entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import toolBox.Maths;

public class TerrainShader extends ShaderProgram{
    private static final String VERTEX_FILE = "src/Shaders/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/Shaders/terrainFragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;


    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
        location_blendMap = super.getUniformLocation("blendMap");
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
    public void loadLight(Light light){
        super.loadVector3f(location_lightPosition,light.getPosition());
        super.loadVector3f(location_lightColour,light.getColour());
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
