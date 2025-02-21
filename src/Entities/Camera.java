package Entities;


import org.joml.Vector3f;
import toolBox.Keyboard;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private float speed = 0.7f;
    private Vector3f position = new Vector3f(0, 10, 0);
    private float pitch;   // height of the cameraa
    private float yaw;     //how much cameera is facing left or right
    private float roll;    // How much the camera is rolling (180 = upside down)

    public Camera() {
    }

    public void move() {
        if (Keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {  // Go down
            speed = 1.8f;
        }else{
            speed = 0.7f;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_W)) {
            position.z -= speed;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_S)) {
            position.z += speed;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_A)) {
            position.x -= speed;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_D)) {
            position.x += speed;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_UP)) {   // Go up
            position.y += speed;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_DOWN)) {   // Go up
            position.y -= speed/2;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_LEFT)) {   // Go up
            yaw -= speed;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_RIGHT)) {   // Go up
            yaw += speed;
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
