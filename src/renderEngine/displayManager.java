package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class displayManager{
    public static int WIDTH = 1280;
    public static int HEIGHT = 700;
    public static int FPS_LIMIT = 120;

    public static String Title = "My Game";

    public displayManager(int width,int height, String title) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.Title = title;
    }

    public static void createDisplay(){

        ContextAttribs attribs = new ContextAttribs(3,2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        //Trys to set the display mode
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            Display.setTitle(Title);
            //Creates the display
            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

        GL11.glViewport(0,0,WIDTH,HEIGHT);




    }

    public static void updateDisplay(){
        Display.sync(FPS_LIMIT);
        Display.update();
    }
    public static void deleteDisplay(){
        Display.destroy();
    }

}
