package br.com.screamer.opengl3initialrancoon.model;


import br.com.screamer.opengl3initialrancoon.enitity.Entity;
import br.com.screamer.opengl3initialrancoon.texture.TextureManager;

public class TexturedModel extends RawModel implements Cloneable {

    public static final String TAG = "TexturedModel";

    private TextureManager texture;

    private float shineDamper = 1;
    private float reflectivity = 0;

    private Entity entity = new Entity(new float[]{0, 0, -15},new float[]{0, 0, 0},new float[]{1f, 1f, 1f});

    public TexturedModel(RawModel rawModel, TextureManager texture) {

        super(rawModel.getId(), rawModel.getCountVertex());

        this.texture = texture;
    }

    public TextureManager getTexture() {

        return texture;
    }

    public Entity getEntity() {

        return entity;
    }

    public void setEntity(Entity entity) {

        this.entity = entity;
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

    // clone() is now overridden and is public.
    public TexturedModel clone() {

        try {
            // call clone in Object.
            return (TexturedModel) super.clone();

        } catch (CloneNotSupportedException e) {

            System.out.println("Cloning not allowed.");
            return this;
        }
    }
}
