package Shaders;


import Entities.Camera;
import Entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import toolBox.Maths;

import java.util.Comparator;
import java.util.List;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/Shaders/vertex.glsl";
    private static final String FRAGMENT_FILE = "src/Shaders/fragment.glsl";

    private static final int MAX_LIGHTS = 4;

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;
    private int location_numberOfRows;
    private int location_offset;

    private int[] location_lightPosition;
    private int[] location_lightColour;
    private int[] location_attenuation;


    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_skyColour = super.getUniformLocation("skyColour");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];


        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");

        }

    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadSkyColour(float r, float g, float b) {
        super.loadVector3f(location_skyColour, new Vector3f(r, g, b));
    }

    public void loadUseFakeLighting(boolean useFakeLighting) {
        super.loadBoolean(location_useFakeLighting, useFakeLighting);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLights(List<Light> lights) {
        for (int j = 0; j < MAX_LIGHTS; j++) {
            if (j < lights.size()) {
                super.loadVector3f(location_lightPosition[j], lights.get(j).getPosition());
                super.loadVector3f(location_lightColour[j], lights.get(j).getColour());
                super.loadVector3f(location_attenuation[j], lights.get(j).getAttenuation());
            } else {
                super.loadVector3f(location_lightPosition[j], new Vector3f(0, 0, 0));
                super.loadVector3f(location_lightColour[j], new Vector3f(0, 0, 0));
                super.loadVector3f(location_attenuation[j], new Vector3f(1, 0, 0));

            }
        }
    }

    public void loadShineVariables(float shineDamper, float reflectivity) {
        super.loadFloat(location_shineDamper, shineDamper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadNumberOfRows(float numberOfRows) {
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadOffset(float offsetX, float offsetY) {
        super.loadVector2f(location_offset, new Vector2f(offsetX, offsetY));
    }
}
