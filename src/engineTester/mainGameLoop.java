package engineTester;

import Entities.Camera;
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
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0



        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22


        };


        RawModel model = loader.loadToVAO(vertices,indices,textureCoords);
        ModelTexture texture = new ModelTexture("res/image.png");
        TexturedModel tModel = new TexturedModel(model,texture);
        Entity cube = new Entity(tModel,new Vector3f(0,0,-1),0,0,0,1);
        Camera camera = new Camera();



        while(!Display.isCloseRequested()){
            camera.move();
            //cube.increasePosition(0,0,-0.01f);
            cube.increaseRotation(0,0,0.5f);
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
