package br.com.screamer.opengl3initialrancoon.model;

import static android.opengl.GLES20.GL_BACK;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glCullFace;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES30.glBindVertexArray;

import android.content.Context;
import android.renderscript.Matrix4f;

import br.com.screamer.opengl3initialrancoon.camera.Camera;
import br.com.screamer.opengl3initialrancoon.enitity.Entity;
import br.com.screamer.opengl3initialrancoon.shader.StaticShader;
import br.com.screamer.opengl3initialrancoon.texture.Texture;
import br.com.screamer.opengl3initialrancoon.util.MapArrayObj;
import br.com.screamer.opengl3initialrancoon.util.Light;
import br.com.screamer.opengl3initialrancoon.util.UtilMath;

/*
* Classe que renderiza um objeto 3D
*
* */

public class RenderTexturedModel3D implements Cloneable {

    public static final String TAG = "TexturedModel";

    private Context context;

    private MapArrayObj iObject;

    private Texture texture;

    private StaticShader shader;

    private Light light;

    private Camera camera;

    private Entity entity = new Entity(new float[]{0, 0, -15},new float[]{0, 0, 0},new float[]{1f, 1f, 1f});

    public RenderTexturedModel3D(Context c, MapArrayObj iObject, Texture texture) {

        shader = new StaticShader(c);
        camera = new Camera();
        light = new Light();
        this.context = c;
        this.texture = texture;
        this.iObject = iObject;
    }


    public RenderTexturedModel3D(Context c, MapArrayObj iObject, Texture texture,
                                 StaticShader shader, Light light, Camera camera, Entity entity) {
        this.context = c;
        this.iObject = iObject;
        this.texture = texture;
        this.shader = shader;
        this.light = light;
        this.camera = camera;
        this.entity = entity;
    }

    // clone() is now overridden and is public.
    public RenderTexturedModel3D clone() {

        try {
            // call clone in Object.
            return (RenderTexturedModel3D) super.clone();

        } catch (CloneNotSupportedException e) {

            System.out.println("Cloning not allowed.");
            return this;
        }
    }

    public void render(){
        //atributos de render
        /*
        * Depth_test
        * clear
        * light
        * camera
        * projection
        * transformation
        * attributes
        * textures
        * cull face
        * */

        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
        //glClear(GL_COLOR_BUFFER_BIT);

        glEnable(GL_CULL_FACE);
        //glCullFace(GL_FRONT);
        glCullFace(GL_BACK);

        shader.onProgram();

        glBindVertexArray(iObject.VAO[0]);

        shader.loadViewMatrix(camera);

        shader.loadLight(light);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        shader.loadShineData(texture.getShineDamper(), texture.getReflectivity());

        //TextureManager.setTexture(texturedModel.getTexture().getTextureId("texture_0"));
        //glActiveTexture(GL_TEXTURE0);
        glBindTexture(0, texture.getTextureID()[0]);

        Matrix4f matrix4f = UtilMath.createTransformationMatrix(
                entity.position,
                entity.rotation,
                entity.scale);

        shader.loadTransformMatrix(matrix4f);

        glDrawElements(GL_TRIANGLES, iObject.countVertex, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);

        shader.offProgram();

        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);

        //texturedModel.getEntity().increasePosition(new float[]{0,0,-.01f});
        //entity.increaseRotation(new float[]{0f, .3f, 0f});

    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public MapArrayObj getiObject() {
        return iObject;
    }

    public void setiObject(MapArrayObj iObject) {
        this.iObject = iObject;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void loadProjection() {

        shader.onProgram();
        shader.loadProjectionMatrix(UtilMath.projectionMatrix);
        shader.offProgram();
    }
}
