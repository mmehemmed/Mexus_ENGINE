package Display;


import org.lwjgl.glfw.GLFW;

public class displayManager {
    public static long WINDOW_ID;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS_CAP = 120;

    public static void createDisplay(String title){
        GLFW.glfwSetWindowSize(WINDOW_ID , WIDTH, HEIGHT);
    }

    public static void updateDisplay(){


    }

    public static void closeDisplay(){

    }
}
