package engineTester;

import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import renderEngine.displayManager;
import org.lwjgl.opengl.Display;

public class mainGameLoop {
    public static void main(String[] args) {
        displayManager display = new displayManager(1280,700,"MY no HOBO game");

        display.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();



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

        RawModel model = loader.loadToVAO(vertices,indices);



        while(!Display.isCloseRequested()){
            renderer.prepare();

            renderer.render(model);

            display.updateDisplay();
        }
        loader.cleanUP();
        display.deleteDisplay();
    }
}
