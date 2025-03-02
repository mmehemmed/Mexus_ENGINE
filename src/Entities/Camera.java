package Entities;


import org.joml.Quaternionf;
import org.joml.Vector3f;
import renderEngine.DisplayManager;
import toolBox.Keyboard;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private float speed = 90f;
    private Vector3f position = new Vector3f(0, 10, 0);
    private float pitch;   // height of the cameraa
    private float yaw;     //how much cameera is facing left or right
    private float roll;    // How much the camera is rolling (180 = upside down)


    private final Vector3f forwardVector = new Vector3f(0,0,-1);
    private final Vector3f upVector = new Vector3f(0,1,0);
    private final Vector3f rightVector = new Vector3f(1,0,0);

    public Camera() {
    }

    public void move() {
        if (Keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {  // Go down
            speed = 120f;
        }else{
            speed = 60f;
        }



        if (Keyboard.isKeyDown(GLFW_KEY_W)) {
            position.fma(speed * DisplayManager.getDelta(),forwardVector);
        }
        if (Keyboard.isKeyDown(GLFW_KEY_S)) {
            position.fma(-speed * DisplayManager.getDelta(),forwardVector);
        }
        if (Keyboard.isKeyDown(GLFW_KEY_D)) {
            position.fma(speed * DisplayManager.getDelta(),rightVector);
        }
        if (Keyboard.isKeyDown(GLFW_KEY_A)) {
            position.fma(-speed * DisplayManager.getDelta(),rightVector);
        }
        if (Keyboard.isKeyDown(GLFW_KEY_UP)) {   // Go up
            position.fma(speed * DisplayManager.getDelta(),upVector);
        }
        if (Keyboard.isKeyDown(GLFW_KEY_DOWN)) {   // Go down
            position.fma(-speed/2 * DisplayManager.getDelta(),upVector);
        }
        if (Keyboard.isKeyDown(GLFW_KEY_LEFT)) {   // Turn Left
            yaw -= speed * DisplayManager.getDelta();
            rotateYaw(speed * DisplayManager.getDelta());
        }
        if (Keyboard.isKeyDown(GLFW_KEY_RIGHT)) {   // Turn right
            yaw += speed * DisplayManager.getDelta();
            rotateYaw(-speed * DisplayManager.getDelta());
        }


    }
    private void rotateYaw(float angle){
        float angleInRadians = (float) Math.toRadians(angle);
        Quaternionf rotation = new Quaternionf().rotateY(angleInRadians);

        rotation.transform(forwardVector);
        rotation.transform(upVector);
        rotation.transform(rightVector);
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
