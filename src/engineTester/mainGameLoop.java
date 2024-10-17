package engineTester;

import Entities.Camera;
import Entities.Entity;
import Models.TexturedModel;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import Models.RawModel;
import renderEngine.OBJLoader;
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




        RawModel model = OBJLoader.loadObjModel("stall",loader);
        ModelTexture texture = new ModelTexture("res/stallTexture.png");
        TexturedModel tModel = new TexturedModel(model,texture);
        Entity cube = new Entity(tModel,new Vector3f(0,0,-30),0,0,0,1);
        Camera camera = new Camera();



        while(!Display.isCloseRequested()){
            camera.move();
            //cube.increasePosition(0,0,-0.01f);
            //cube.increaseRotation(0,0.2f,0);
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(cube,shader);
            shader.stop();
            display.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUP();
        display.deleteDisplay();
    }
}
