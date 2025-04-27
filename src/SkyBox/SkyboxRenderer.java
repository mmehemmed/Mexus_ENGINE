package SkyBox;

import Entities.Camera;
import Models.RawModel;
import org.joml.Matrix4f;
import renderEngine.Loader;
import static org.lwjgl.opengl.GL40.*;

public class SkyboxRenderer {
    private static final float SIZE = 500f;

    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,


            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,


            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,


            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,


            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,


            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

    private static String[] TExTURE_FILES = {"right","left","top","bottom","back","front"};

    private RawModel cube;
    private int texture;
    private SkyboxShader shader;

    public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix) {
        cube = loader.loadToVAO(VERTICES,3);
        texture = loader.loadCubeMapTexture(TExTURE_FILES);
        shader = new SkyboxShader();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    public void render(Camera camera){
        shader.start();
        shader.loadViewMatrix(camera);
        glBindVertexArray(cube.getVaoID());
        glEnableVertexAttribArray(0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP,texture);
        glDrawArrays(GL_TRIANGLES,0,cube.getVertexCount());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.stop();
    }
}
