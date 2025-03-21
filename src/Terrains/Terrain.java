package Terrains;

import Models.RawModel;
import OBJConverter.ModelData;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderEngine.Loader;
import toolBox.Maths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Terrain {
    private static final float SIZE = 800;
    private static final float MAX_TERRAIN_HEIGHT = 40.0f;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;



    private float x;
    private float z;
    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;
    private RawModel model;
    private float[][] heights;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack,TerrainTexture blendMap,String heightMap) {
        this.texturePack = texturePack;
        this.blendMap = blendMap;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader,heightMap);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    public RawModel getModel() {
        return model;
    }

    private RawModel generateTerrain(Loader loader,String heightMap) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + heightMap + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int VERTEX_COUNT = image.getHeight();
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                float height = getHeight(j,i,image);
                heights[j][i] = height;

                vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = height;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(j,i,image);

                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(new ModelData(vertices,textureCoords,normals,indices,0));

    }
    private float getHeight(int x,int y,BufferedImage image){
        if(x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight() ){
            return 0;
        }
        float height = image.getRGB(x,y);
        height+=MAX_PIXEL_COLOR/2f;
        height/=MAX_PIXEL_COLOR/2f;
        height*=MAX_TERRAIN_HEIGHT;
        return height;
    }
    public float getHeightOfTerrain(float worldX,float worldZ){
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSquareSize = SIZE / ((float) heights.length - 1);
        int gridX = (int) Math.floor(terrainX/gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ/gridSquareSize);
        if(gridX < 0 || gridX >= heights.length-1 || gridZ < 0 || gridZ >= heights.length-1 ){
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
        float answer;
        if (xCoord <= (1-zCoord)) {
            answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ], 0), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        } else {
            answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }

        return answer;
    }
    private Vector3f calculateNormal(int x, int z,BufferedImage image){
        float heightL = getHeight(x-1,z,image);
        float heightR = getHeight(x+1,z,image);
        float heightU = getHeight(x,z-1,image);
        float heightD = getHeight(x,z+1,image);
        Vector3f normal = new Vector3f();
        normal.x = heightL - heightR;
        normal.y = 2.0f;
        normal.z = heightD - heightU;
        normal.normalize();

        return normal;
    }
}