package Shaders;


import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
        private static final String VERTEX_FILE = "src/Shaders/vertex.glsl";
        private static final String FRAGMENT_FILE = "src/Shaders/fragment.glsl";

        private int location_transformationMatrix;
        private int location_projectionMatrix;



    public StaticShader() {
            super(VERTEX_FILE, FRAGMENT_FILE);
        }

        @Override
        protected void getAllUniformLocation() {
            location_transformationMatrix = super.getUniformLocation("transformationMatrix");
            location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        }

        @Override
        protected void bindAttributes() {
            super.bindAttribute(0, "position");
            super.bindAttribute(1,"textureCoords");
        }

        public void loadTransformationMatrix(Matrix4f matrix){
            super.loadMatrix(location_transformationMatrix,matrix);
        }
        public void loadProjectionMatrix(Matrix4f projection){
            super.loadMatrix(location_projectionMatrix,projection);
        }



    }
