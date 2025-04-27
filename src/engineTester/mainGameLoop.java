package engineTester;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import GUI.GUIRenderer;
import GUI.GUITexture;
import Models.TexturedModel;

import OBJConverter.OBJFileLoader;
import Terrains.Terrain;
import Textures.ModelTexture;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
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
        MasterRenderer renderer = new MasterRenderer(loader);
        GUIRenderer guiRenderer = new GUIRenderer(loader);
        Camera camera = new Camera();


        //       TERRAiN TEXTURES
        TerrainTexture grassy = new TerrainTexture(loader.loadTexture("res/Textures/groundTexture.png"));
        TerrainTexture path = new TerrainTexture(loader.loadTexture("res/Textures/path.png"));
        TerrainTexture flowery = new TerrainTexture(loader.loadTexture("res/Textures/flowery.png"));
        TerrainTexture dirt = new TerrainTexture(loader.loadTexture("res/Textures/dirt.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("res/Textures/blendMap.png"));

        TerrainTexturePack texturePack = new TerrainTexturePack(grassy, dirt, flowery, path);

        //GUITexture g = new GUITexture(new Vector2f(0.5f,0.5f),new Vector2f(0.25f,0.25f),loader.loadTexture("res/Textures/terrainHeightMap.png"));
        List<GUITexture> guis = new ArrayList<>();
        //guis.add(g);

        RawModel treeModel = loader.loadToVAO(OBJFileLoader.loadOBJ("Models/tree"));
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("res/Textures/tree.png"));
        TexturedModel tree = new TexturedModel(treeModel, treeTexture);

        RawModel pineModel = loader.loadToVAO(OBJFileLoader.loadOBJ("Models/pine"));
        ModelTexture pineTexture = new ModelTexture(loader.loadTexture("res/Textures/pine.png"));
        TexturedModel pine = new TexturedModel(pineModel, pineTexture);

        RawModel fernModel = loader.loadToVAO(OBJFileLoader.loadOBJ("Models/fern"));
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("res/Textures/fern.png"));
        fernTexture.setTransparency(true);
        fernTexture.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(fernModel, fernTexture);

        RawModel lampModel = loader.loadToVAO(OBJFileLoader.loadOBJ("Models/lamp"));
        ModelTexture lampTexture = new ModelTexture(loader.loadTexture("res/Textures/lamp.png"));
        TexturedModel lamp = new TexturedModel(lampModel,lampTexture);

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "Textures/heightMap");
        Terrain terrain2 = new Terrain(-1,-1,loader,texturePack,blendMap,"Textures/terrainHeightMap");
        List<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain);
        terrains.add(terrain2);

        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            float TreeX = Maths.randomFloat(0, 800);
            float TreeZ = Maths.randomFloat(-800, 0);
            float TreeY = terrain.getHeightOfTerrain(TreeX, TreeZ);
            entities.add(new Entity(tree, new Vector3f(TreeX, TreeY, TreeZ), 0, Maths.randomFloat(0, 359), 0, Maths.randomFloat(12, 13f)));

            TreeX = Maths.randomFloat(0, 800);
            TreeZ  = Maths.randomFloat(-800, 0);
            TreeY = terrain.getHeightOfTerrain(TreeX, TreeZ);
            entities.add(new Entity(pine, new Vector3f(TreeX, TreeY, TreeZ), 0, Maths.randomFloat(0, 359), 0, Maths.randomFloat(5, 5.2f)));

            TreeX = Maths.randomFloat(0, 800);
            TreeZ = Maths.randomFloat(-800, 0);
            TreeY = terrain.getHeightOfTerrain(TreeX, TreeZ);
            entities.add(new Entity(fern, Maths.randomInteger(0, 4), new Vector3f(TreeX, TreeY, TreeZ), 0, Maths.randomFloat(0, 359), 0, Maths.randomFloat(3, 4.2f)));
        }



        List<Light> lights= new ArrayList<>();

        lights.add(new Light(new Vector3f(10,-35,-10),new Vector3f(0,0,1),new Vector3f(0.8f,0.002f,0.0001f)));
        entities.add(new Entity(lamp, new Vector3f(10,-35,-10),0,0,0,1.0f));

        lights.add(new Light(new Vector3f(790,-35,-10),new Vector3f(0,0,1),new Vector3f(0.8f,0.002f,0.0001f)));
        entities.add(new Entity(lamp, new Vector3f(790,-35,-10),0,0,0,1.0f));

        lights.add(new Light(new Vector3f(10,-35,-790),new Vector3f(0,0,1),new Vector3f(0.8f,0.002f,0.0001f)));
        entities.add(new Entity(lamp, new Vector3f(10,-35,-790),0,0,0,1.0f));

        lights.add(new Light(new Vector3f(790,-35,-790),new Vector3f(0,0,1),new Vector3f(0.8f,0.002f,0.0001f)));
        entities.add(new Entity(lamp, new Vector3f(790,-35,-790),0,0,0,1.0f));

        lights.add(new Light(new Vector3f(400,-35,-400),new Vector3f(0,0,1),new Vector3f(0.8f,0.002f,0.0001f)));
        entities.add(new Entity(lamp, new Vector3f(400,-35,-400),0,0,0,1.0f));

        while (!glfwWindowShouldClose(DisplayManager.window)) {
            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_Z)) {
                if (renderMode) {
                    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                } else {
                    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                }
                renderMode = !renderMode;
            }

            //renderer.processTerrain(terrain2);
            terrains.forEach(renderer::processTerrain);
            entities.forEach(renderer::processEntities);
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            //torch.setPosition(new Vector3f(torch.getPosition().x,torch.getPosition().y,torch.getPosition().z+3f));

            camera.move();
            //sun.setPosition(camera.getPosition());

            DisplayManager.updateDisplay();
            Keyboard.update();
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
