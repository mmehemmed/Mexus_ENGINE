package toolBox;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Keyboard extends GLFWKeyCallback {
    private static boolean[] keys = new boolean[65536];
    private static boolean[] wasPressed = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < 0) return; // Prevent ArrayIndexOutOfBoundsException
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public static boolean isKeyPressed(int keycode) {
        if (keys[keycode] && !wasPressed[keycode]) {
            wasPressed[keycode] = true;
            return true;
        }
        return false;
    }

    public static void update() {
        // Reset keys that were released
        for (int i = 0; i < wasPressed.length; i++) {
            if (!keys[i]) {
                wasPressed[i] = false;
            }
        }
    }
}
