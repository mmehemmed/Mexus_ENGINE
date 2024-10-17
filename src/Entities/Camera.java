package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;   // height of the camera
    private float yaw;     //how much cameera is facing left or right
    private float roll;    // How much the camera is rolling (180 = upside down)

    public Camera(){
    }
    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= 0.06f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += 0.06f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x -= 0.06f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x += 0.06f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_K)){
            yaw += 0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_J)){
            yaw -= 0.2f;
        }

    }
    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
