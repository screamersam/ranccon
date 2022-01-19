package br.com.screamer.opengl3initialrancoon.texture;

public class Texture {

    private int[] textureID;
    private float shineDamper = 1;
    private float reflectivity = 0;

    public Texture(int[] textureID) {
        this.textureID = textureID;
    }

    public Texture(int[] textureID, float shineDamper, float reflectivity) {
        this.textureID = textureID;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
    }

    public int[] getTextureID() {
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
}
