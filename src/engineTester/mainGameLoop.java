package engineTester;

import Models.TexturedModel;
import Shaders.StaticShader;
import Textures.ModelTexture;
import renderEngine.Loader;
import Models.RawModel;
import renderEngine.Renderer;
import renderEngine.displayManager;
import org.lwjgl.opengl.Display;

public class mainGameLoop {
    public static void main(String[] args) {
        displayManager display = new displayManager(1280,700,"MY no HOBO game");

        display.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();



        float[] vertices = {
                -0.5f,0.5f,0f,
                -0.5f,-0.5f,0f,
                0.5f,-0.5f,0f,
                0.5f,0.5f,0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };
        float[] tCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model = loader.loadToVAO(vertices,indices,tCoords);
        ModelTexture texture = new ModelTexture("res/image.png");
        TexturedModel tModel = new TexturedModel(model,texture);

        while(!Display.isCloseRequested()){
            renderer.prepare();
            shader.start();
            renderer.render(tModel);
            shader.stop();
            display.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUP();
        display.deleteDisplay();
    }
}
