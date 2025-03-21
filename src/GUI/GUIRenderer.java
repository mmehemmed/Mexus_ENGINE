package GUI;

import Models.RawModel;
import org.joml.Matrix4f;
import renderEngine.Loader;
import toolBox.Maths;

import static org.lwjgl.opengl.GL40.*;

import java.util.List;

public class GUIRenderer {
    private final RawModel quad;
    private GuiShader shader;

    public GUIRenderer(Loader loader) {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVAO(positions);
        shader = new GuiShader();
    }
    public void render(List<GUITexture> guis){
        glBindVertexArray(quad.getVaoID());
        glEnableVertexAttribArray(0);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        glBlendFunc(GL_BLEND_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
        shader.start();
        for(GUITexture gui:guis){
            Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(),gui.getScale());
            shader.loadTransformation(matrix);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D,gui.getTexture());

            glDrawArrays(GL_TRIANGLE_STRIP,0,quad.getVertexCount());
        }
        shader.stop();
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
    public void cleanUp(){
        shader.cleanUp();
    }
}
