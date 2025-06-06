package Textures;


public class ModelTexture {

    private int textureID;

    private float shineDamper = 1;
    private float reflectivity = 0;

    private int numberOfRows = 1;

    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    public ModelTexture(int texture){
        this.textureID = texture;
    }

    public int getID(){
        return textureID;
    }


    public float getShineDamper() {
        return shineDamper;
    }


    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }


    public float getReflectivity() {
        return reflectivity;
    }


    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public boolean HasTransparency() {
        return hasTransparency;
    }

    public void setTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public boolean getUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
