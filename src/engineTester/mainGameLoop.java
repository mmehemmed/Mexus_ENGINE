package engineTester;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;

import Textures.ModelTexture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import Models.RawModel;
import org.lwjgl.opengl.Display;
import toolBox.Maths;


import java.util.ArrayList;
import java.util.List;

public class mainGameLoop {
    public static void main(String[] args) {
        boolean renderMode = true;
        displayManager display = new displayManager(1280,700,"3d Game");
        display.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();


        Light torch = new Light(new Vector3f(0,0,-10),new Vector3f(1, 1, 1));
        Camera camera = new Camera();


        RawModel model = OBJLoader.loadObjModel("dragon",loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/dragonTexture.png"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel tModel = new TexturedModel(model,texture);


        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Entity dragon = new Entity(tModel,new Vector3f(Maths.randomFloat(-20,20),Maths.randomFloat(-15,15),Maths.randomFloat(-5,-80)),Maths.randomFloat(-300,300),Maths.randomFloat(-300,300),Maths.randomFloat(-300,300),0.5f);
            entities.add(dragon);
        }




        while(!Display.isCloseRequested()){
            if(Keyboard.next() && Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_Z){
                if(renderMode) {
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                }else{
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                }
                renderMode = !renderMode;
            }
            camera.move();
            entities.forEach(renderer::processEntities);
            renderer.render(torch,camera);
            display.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUP();
        display.deleteDisplay();
    }
}
