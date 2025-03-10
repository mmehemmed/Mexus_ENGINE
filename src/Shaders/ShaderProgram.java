package Shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL40.*;

public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile,String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformLocation();
    }

    public void start(){
        glUseProgram(programID);
    }

    public void stop(){
        glUseProgram(0);
    }

    public void cleanUp(){
        stop();
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }

    protected int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID,uniformName);
    }

    protected abstract void getAllUniformLocation();

    protected void loadFloat(int location, float value){
        glUniform1f(location,value);
    }
    protected void loadInteger(int location, int value){
        glUniform1i(location,value);
    }

    protected void loadVector3f(int location, Vector3f vector){
        glUniform3f(location,vector.x,vector.y,vector.z);
    }
    protected void loadVector2f(int location, Vector2f vector){
        glUniform2f(location,vector.x,vector.y);
    }

    protected void loadBoolean(int location, boolean bool){
        float toLoad = 0;
        if (bool == true){
            toLoad = 1;
        }
        glUniform1f(location,toLoad);
    }
    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.get(matrixBuffer);
        glUniformMatrix4fv(location, false, matrixBuffer);
    }


    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        glBindAttribLocation(programID, attribute, variableName);
    }

    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS )== GL_FALSE){
            System.out.println(glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

}