package Entities;

import Models.TexturedModel;
import org.joml.Vector3f;

public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rx,ry,rz;
    private float scale;

    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position, float rx, float ry, float rz, float scale) {
        this.model = model;
        this.position = position;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }
    public Entity(TexturedModel model,int index,Vector3f position, float rx, float ry, float rz, float scale) {
        this.model = model;
        this.textureIndex = index;
        this.position = position;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public  void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;

    }
    public  void increaseRotation(float dx, float dy,float dz){
        this.rx += dx;
        this.ry += dy;
        this.rz += dz;

    }


    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public float getRz() {
        return rz;
    }

    public void setRz(float rz) {
        this.rz = rz;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getTextureOffsetX(){
        int column = textureIndex%model.getTexture().getNumberOfRows();
        return (float) column /model.getTexture().getNumberOfRows();
    }
    public float getTextureOffsetY(){
        int row = textureIndex/model.getTexture().getNumberOfRows();
        return (float) row /model.getTexture().getNumberOfRows();
    }
}

