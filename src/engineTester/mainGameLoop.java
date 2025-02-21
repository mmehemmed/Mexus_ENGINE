package engineTester;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;

import Terrains.Terrain;
import Textures.ModelTexture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import renderEngine.*;
import Models.RawModel;
import toolBox.Maths;
import toolBox.Keyboard;

import static org.lwjgl.opengl.GL40.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class mainGameLoop {
    public static void main(String[] args) {
        boolean renderMode = true;
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();


        Light torch = new Light(new Vector3f(100, 30, 2), new Vector3f(1, 1, 1));
        Camera camera = new Camera();


        RawModel treeModel = OBJLoader.loadObjModel("Models/tree", loader);
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("res/Textures/tree.png"));
        treeTexture.setShineDamper(1);
        treeTexture.setReflectivity(0);
        TexturedModel tree = new TexturedModel(treeModel, treeTexture);

        RawModel fernModel = OBJLoader.loadObjModel("Models/fern", loader);
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("res/Textures/fern.png"));
        fernTexture.setTransparency(true);
        TexturedModel fern = new TexturedModel(fernModel, fernTexture);

        RawModel grassModel = OBJLoader.loadObjModel("Models/grassModel", loader);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("res/Textures/grassTexture.png"));
        grassTexture.setTransparency(true);
        grassTexture.setUseFakeLighting(true);
        TexturedModel grass = new TexturedModel(grassModel, grassTexture);





        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            entities.add(new Entity(tree,new Vector3f(Maths.randomFloat(0,800),0,Maths.randomFloat(-800,800)),0,Maths.randomFloat(0,359),0,Maths.randomFloat(5,5.9f)));
            entities.add(new Entity(fern,new Vector3f(Maths.randomFloat(0,800),0,Maths.randomFloat(-800,800)),0,Maths.randomFloat(0,359),0,Maths.randomFloat(1,1.2f)));
            entities.add(new Entity(grass,new Vector3f(Maths.randomFloat(0,800),0,Maths.randomFloat(-800,800)),0,Maths.randomFloat(0,359),0,Maths.randomFloat(1.4f,1.5f)));
        }




        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("res/Textures/groundTexture.png")));
        Terrain terrain2 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("res/Textures/groundTexture.png")));

        while (!glfwWindowShouldClose(DisplayManager.window)) {
            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_Z)) {
                if (renderMode) {
                    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                } else {
                    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                }
                renderMode = !renderMode;
            }
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            entities.forEach(renderer::processEntities);
            camera.move();
            renderer.render(torch, camera);
            //torch.setPosition(new Vector3f(torch.getPosition().x+0.1f,torch.getPosition().y,torch.getPosition().z));
            DisplayManager.updateDisplay();
            Keyboard.update();
        }
        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
