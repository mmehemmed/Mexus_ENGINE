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
import org.joml.Vector2f;
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

        GUIRenderer guiRenderer = new GUIRenderer(loader);


//        Light lightSun = new Light(new Vector3f(220, 350, -250), new Vector3f(1, 1, 1));
//        Light lightTorch = new Light(new Vector3f(15, 40, -250), new Vector3f(0, 0.2f, 1));
//
//
//        List<Light> lights= new ArrayList<>();
//        lights.add(lightSun);
//        lights.add(lightTorch);


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
        treeTexture.setShineDamper(1);
        treeTexture.setReflectivity(0);
        TexturedModel tree = new TexturedModel(treeModel, treeTexture);

        RawModel fernModel = loader.loadToVAO(OBJFileLoader.loadOBJ("Models/fern"));
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("res/Textures/fern.png"));
        fernTexture.setTransparency(true);
        fernTexture.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(fernModel, fernTexture);

//        RawModel grassModel = loader.loadToVAO(OBJFileLoader.loadOBJ("Models/grassModel"));
//        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("res/Textures/grassTexture.png"));
//        grassTexture.setTransparency(true);
//        grassTexture.setUseFakeLighting(true);
//        TexturedModel grass = new TexturedModel(grassModel, grassTexture);
//
//        ModelTexture flowerTexture = new ModelTexture(loader.loadTexture("res/Textures/flower.png"));
//        grassTexture.setTransparency(true);
//        grassTexture.setUseFakeLighting(true);
//        TexturedModel flower = new TexturedModel(grassModel, flowerTexture);

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "Textures/terrainHeightMap");
        //Terrain terrain2 = new Terrain(-1,-1,loader,texturePack,blendMap,"Textures/terrainHeightMap");

        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            float TreeX = Maths.randomFloat(0, 800);
            float TreeZ = Maths.randomFloat(-800, 0);
            float TreeY = terrain.getHeightOfTerrain(TreeX, TreeZ);
            entities.add(new Entity(tree, new Vector3f(TreeX, TreeY, TreeZ), 0, Maths.randomFloat(0, 359), 0, Maths.randomFloat(5, 5.9f)));
            float FernX = Maths.randomFloat(0, 800);
            float FernZ = Maths.randomFloat(-800, 0);
            float FernY = terrain.getHeightOfTerrain(FernX, FernZ);
            entities.add(new Entity(fern, Maths.randomInteger(0, 4), new Vector3f(FernX, FernY, FernZ), 0, Maths.randomFloat(0, 359), 0, Maths.randomFloat(1, 1.2f)));


//            float GrassX = Maths.randomFloat(0,800);
//            float GrassZ = Maths.randomFloat(-800,0);
//            float GrassY = terrain.getHeightOfTerrain(GrassX, GrassZ);
//            entities.add(new Entity(grass,new Vector3f(GrassX, GrassY, GrassZ),0,Maths.randomFloat(0,359),0,Maths.randomFloat(1.4f,1.5f)));
//            float FlowerX = Maths.randomFloat(0,800);
//            float FlowerZ = Maths.randomFloat(-800,0);
//            float FlowerY = terrain.getHeightOfTerrain(FlowerX, FlowerZ);
//            entities.add(new Entity(flower,new Vector3f(FlowerX, FlowerY, FlowerZ),0,Maths.randomFloat(0,359),0,Maths.randomFloat(3.2f,4.5f)));
        }
//        entities.add(new Entity(tree, lightSun.getPosition(), 0, 0, 0, 30.0f));
//        entities.add(new Entity(tree, lightTorch.getPosition(), 0, 0, 0, 30.0f));
        List<Light> lights= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Vector3f pos = new Vector3f(Maths.randomFloat(0, 800),60,Maths.randomFloat(-800, 0));
            Vector3f col = new Vector3f(Maths.randomFloat(0, 1),Maths.randomFloat(0, 1),Maths.randomFloat(0, 1));
            lights.add(new Light(pos,col));
            entities.add(new Entity(tree,pos,0,0,0,10.0f));
        }



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
            entities.forEach(renderer::processEntities);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            //torch.setPosition(new Vector3f(torch.getPosition().x,torch.getPosition().y,torch.getPosition().z+3f));
            DisplayManager.updateDisplay();
            Keyboard.update();
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
