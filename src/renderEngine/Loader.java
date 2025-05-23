package renderEngine;

import Models.RawModel;
import OBJConverter.ModelData;
import Textures.Texture;
import Textures.TextureData;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL40.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

//              PUBLIC METHODS

    public RawModel loadToVAO(ModelData modelData){
        int vaoID = createVAO();
        bindIndicesBuffer(modelData.getIndices());
        storeDataInAttributeList(0,modelData.getVertices(),3);
        storeDataInAttributeList(1,modelData.getTextureCoords(),2);
        storeDataInAttributeList(2,modelData.getNormals(),3);
        glEnableVertexAttribArray(0);
        unbindVAO();

        return new RawModel(vaoID, modelData.getIndices().length);
    }
    public RawModel loadToVAO(float[] positions,int dimensions) {
        int vaoID = createVAO();
        storeDataInAttributeList(0,positions,dimensions);
        unbindVAO();
        return new RawModel(vaoID,positions.length/dimensions);
    }
    public int loadCubeMapTexture(String[] textureNames){
        int textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP,textureID);

        for (int i = 0; i < textureNames.length; i++) {
            TextureData data = decodeTextureFile("res/Textures/" + textureNames[i]+".png");
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i,0,GL_RGBA,data.getWidth(),data.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,data.getBuffer());
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        textures.add(textureID);
        return textureID;
    }
    private TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try {
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ", didn't work");
            System.exit(-1);
        }
        return new TextureData(buffer, width, height);
    }

    public int loadTexture(String filename) {
        Texture texture;
        try {
            texture = new Texture(filename);
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_LOD_BIAS,-0.4f);
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
