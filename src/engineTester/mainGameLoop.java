package engineTester;

import Entities.Entity;
import Models.TexturedModel;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import Models.RawModel;
import renderEngine.Renderer;
import renderEngine.displayManager;
import org.lwjgl.opengl.Display;

public class mainGameLoop {
    public static void main(String[] args) {
        displayManager display = new displayManager(1280,700,"3d Game");

        display.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


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
        Entity cube = new Entity(tModel,new Vector3f(0,0,-1),0,0,0,1);

        while(!Display.isCloseRequested()){
            cube.increasePosition(0,0,-0.01f);
            cube.increaseRotation(0,0,0.5f);
            renderer.prepare();
            shader.start();
            renderer.render(cube,shader);
            shader.stop();
            display.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUP();
        display.deleteDisplay();
    }
}
