package br.com.screamer.opengl3initialrancoon.model;

import static android.opengl.GLES20.GL_FRONT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_LINE_STRIP;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES30.GL_BACK;
import static android.opengl.GLES30.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES30.GL_CULL_FACE;
import static android.opengl.GLES30.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES30.GL_DEPTH_TEST;
import static android.opengl.GLES30.GL_TRIANGLES;
import static android.opengl.GLES30.GL_UNSIGNED_INT;
import static android.opengl.GLES30.glBindVertexArray;
import static android.opengl.GLES30.glClear;
import static android.opengl.GLES30.glCullFace;
import static android.opengl.GLES30.glDisable;
import static android.opengl.GLES30.glDisableVertexAttribArray;
import static android.opengl.GLES30.glDrawElements;
import static android.opengl.GLES30.glEnable;
import static android.opengl.GLES30.glEnableVertexAttribArray;

import android.content.Context;
import android.renderscript.Matrix4f;

import br.com.screamer.opengl3initialrancoon.camera.Camera;
import br.com.screamer.opengl3initialrancoon.enitity.Entity;
import br.com.screamer.opengl3initialrancoon.shader.StaticShader;
import br.com.screamer.opengl3initialrancoon.texture.TextureManager;
import br.com.screamer.opengl3initialrancoon.util.Light;
import br.com.screamer.opengl3initialrancoon.util.LoaderManagerObject;
import br.com.screamer.opengl3initialrancoon.util.UtilMath;

public class MyModel3D {

    TexturedModel texturedModel;

    StaticShader shader;

    Light light;

    Camera camera;

    Entity entity;

    public MyModel3D(Context context, LoaderManagerObject loaderManagerObject, int resourceObject, int resourceTexture) {

        texturedModel = LoadModel.loadTexturedModel(
                context,
                resourceObject,
                resourceTexture,
                loaderManagerObject
        );

        entity = new Entity();
        entity.position = new float[]{2,0,-30};
        entity.scale = new float[]{0.05f,0.05f,0.05f};

        shader = new StaticShader(context);

        light = new Light();

        camera = new Camera();
    }

    //capturar quando fazer o carregamento de tela
    public void loadProjection(){

        shader.onProgram();
        shader.loadProjectionMatrix(UtilMath.projectionMatrix);
        shader.offProgram();
    }


    public void render(){

        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
        //glClear(GL_COLOR_BUFFER_BIT);

        shader.onProgram();

        glBindVertexArray(texturedModel.getId());

        shader.loadViewMatrix(camera);

        shader.loadLight(light);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        shader.loadShineData(texturedModel.getShineDamper(), texturedModel.getReflectivity());

        //TextureManager.setTexture(texturedModel.getTexture().getTextureId("texture_0"));
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(0, texturedModel.getTexture().getTextureId("texture_0")[0]);

        Matrix4f matrix4f = UtilMath.createTransformationMatrix(
                entity.position,
                entity.rotation,
                entity.scale);

        shader.loadTransformMatrix(matrix4f);

        glEnable(GL_CULL_FACE);
        //glCullFace(GL_FRONT);
        glCullFace(GL_BACK);

        glDrawElements(GL_TRIANGLES, texturedModel.getCountVertex(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);

        shader.offProgram();

        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);

        //texturedModel.getEntity().increasePosition(new float[]{0,0,-.01f});
        entity.increaseRotation(new float[]{0f, .3f, 0f});
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public StaticShader getShader() {
        return shader;
    }

    public Light getLight() {
        return light;
    }

    public Camera getCamera() {
        return camera;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public void setShader(StaticShader shader) {
        this.shader = shader;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
