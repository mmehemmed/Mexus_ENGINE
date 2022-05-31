package engineTester;

import Display.displayManager;
import org.lwjgl.opengl.Display;

public class mainGameLoop {
    public static void main(String[] args) {
        displayManager display = new displayManager();

        display.createDisplay();


        while(!Display.isCloseRequested()){
            display.updateDisplay();
        }
        display.deleteDisplay();
    }
}
