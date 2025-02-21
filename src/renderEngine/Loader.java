package renderEngine;

import Models.RawModel;
import Textures.Texture;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL40.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

//              PUBLIC METHODS

    public RawModel loadToVAO(float[] posistions, float[] textureCoords,float[] normals,int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);;
        storeDataInAttributeList(0,posistions,3);
        storeDataInAttributeList(1,textureCoords,2);
        storeDataInAttributeList(2,normals,3);
        glEnableVertexAttribArray(0);
        unbindVAO();

        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String filename) {
        Texture texture;
        try {
            texture = new Texture(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }


//              PRIVATE METHODS


    private int createVAO(){
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    public void cleanUP(){
        for (int vao:vaos){
            glDeleteVertexArrays(vao);
        }
        for (int vbo:vbos){
            glDeleteBuffers(vbo);
        }
        for (int texture:textures){
            glDeleteTextures(texture);
        }
    }
    private void storeDataInAttributeList(int attributenumber,float[] data,int coordSize){
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(attributenumber,coordSize, GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    private void unbindVAO(){
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){
        int vboId = glGenBuffers();
        vbos.add(vboId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }


}
