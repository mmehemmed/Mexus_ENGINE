package renderEngine;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import Shaders.StaticShader;
import Shaders.TerrainShader;
import SkyBox.SkyboxRenderer;
import Terrains.Terrain;
import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL40.*;

import java.util.*;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private static final float RED = 0.5444f;
    private static final float GREEN = 0.62f;
    private static final float BLUE = 0.69f;

    private StaticShader entityShader = new StaticShader();
    private EntityRenderer entityRenderer;

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;

    private SkyboxRenderer skyboxRenderer;

    private Matrix4f projectionMatrix;
    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();


    public MasterRenderer(Loader loader) {
        enableCulling();
        createProjectionMatrix();
        this.entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(terrainShader,projectionMatrix);
        this.skyboxRenderer = new SkyboxRenderer(loader,projectionMatrix);
    }
    public static void enableCulling(){
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }
    public static void disableCulling(){
        glDisable(GL_CULL_FACE);
    }

    public void render(List<Light> Lights, Camera camera) {
        Lights.sort(Comparator.comparingDouble(v -> v.getPosition().distanceSquared(camera.getPosition())));
        prepare();
        entityShader.start();
        entityShader.loadSkyColour(RED,GREEN,BLUE);
        entityShader.loadLights(Lights);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        entityShader.stop();
        terrainShader.start();
        terrainShader.loadSkyColour(RED,GREEN,BLUE);
        terrainShader.loadLights(Lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        skyboxRenderer.render(camera);
        terrains.clear();
        entities.clear();
    }
    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }
    public void processEntities(Entity entity) {
        TexturedModel model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(model, newBatch);
        }
    }

    public void prepare() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(RED,GREEN,BLUE,1);

    }

    private void createProjectionMatrix() {
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(FOV), (float) DisplayManager.getWindowWidth() / (float) DisplayManager.getWindowHeight(), NEAR_PLANE, FAR_PLANE);
    }

    public void cleanUp() {
        entityShader.cleanUp();
        terrainShader.cleanUp();
    }
}
