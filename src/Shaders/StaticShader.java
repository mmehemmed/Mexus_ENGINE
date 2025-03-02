package Shaders;


import Entities.Camera;
import Entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import toolBox.Maths;

public class StaticShader extends ShaderProgram {
        private static final String VERTEX_FILE = "src/Shaders/vertex.glsl";
        private static final String FRAGMENT_FILE = "src/Shaders/fragment.glsl";

        private int location_transformationMatrix;
        private int location_projectionMatrix;
        private int location_viewMatrix;
        private int location_lightPosition;
        private int location_lightColour;
        private int location_shineDamper;
        private int location_reflectivity;
        private int location_useFakeLighting;
        private int location_skyColour;
        private int location_numberOfRows;
        private int location_offset;

    public StaticShader() {
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
            location_useFakeLighting = super.getUniformLocation("useFakeLighting");
            location_skyColour = super.getUniformLocation("skyColour");
            location_numberOfRows = super.getUniformLocation("numberOfRows");
            location_offset = super.getUniformLocation("offset");
        }

        @Override
        protected void bindAttributes() {
            super.bindAttribute(0, "position");
            super.bindAttribute(1,"textureCoords");
            super.bindAttribute(2,"normal");
        }
        public void loadSkyColour(float r,float g,float b){
            super.loadVector3f(location_skyColour,new Vector3f(r,g,b));
        }
        public void loadUseFakeLighting(boolean useFakeLighting){
            super.loadBoolean(location_useFakeLighting,useFakeLighting);
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
        public void loadNumberOfRows(float numberOfRows){
            super.loadFloat(location_numberOfRows,numberOfRows);
        }
        public void loadOffset(float offsetX,float offsetY){
            super.loadVector2f(location_offset,new Vector2f(offsetX,offsetY));
        }
    }
