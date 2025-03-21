package GUI;


import org.joml.Vector2f;


public class GUITexture {
    private int texture;
    private Vector2f position;
    private Vector2f scale;


    public GUITexture(Vector2f scale, Vector2f position, int texture) {
        this.scale = scale;
        this.position = position;
        this.texture = texture;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
