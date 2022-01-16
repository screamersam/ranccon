package br.com.screamer.opengl3initialrancoon.model;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES30.glBindVertexArray;

import android.renderscript.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import br.com.screamer.opengl3initialrancoon.camera.Camera;
import br.com.screamer.opengl3initialrancoon.enitity.Entity;
import br.com.screamer.opengl3initialrancoon.shader.StaticShader;
import br.com.screamer.opengl3initialrancoon.texture.TextureManager;
import br.com.screamer.opengl3initialrancoon.util.Light;
import br.com.screamer.opengl3initialrancoon.util.UtilMath;

public class MultipleTexturedModel {

    private StaticShader shader;
    private List<TexturedModel> texturedModels = new ArrayList<>();

    public MultipleTexturedModel(StaticShader shader) {

        this.shader = shader;
    }

    public void setTexturedModels(List<TexturedModel> texturedModels) {
        this.texturedModels = texturedModels;
    }

    public TexturedModel getTexturedModel(int index) {

        return texturedModels.get(index);
    }

    public void initProjection(int width, int height){

        UtilMath.projectionMatrix = UtilMath.createProjectionMatrix(width, height);
        shader.onProgram();
        shader.loadProjectionMatrix(UtilMath.projectionMatrix);
        shader.offProgram();
    }

    public void prepare(){

        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
        glClearColor(0f,0f,0f,1f);
    }

    public void draw(Light sun, Camera camera){

        prepare();
        shader.onProgram();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        draw(texturedModels);
        shader.offProgram();
        //limpar entidades
    }

    //ativa objeto
    public void prepareObject(TexturedModel tm){

        glBindVertexArray(tm.getId());

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        //TextureManager.setTexture(tm.getTexture().getTextureId());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(0,tm.getTexture().getTextureId("texture_0")[0]);

        shader.loadShineData(tm.getShineDamper(), tm.getReflectivity());
    }

    public void prepareInstance(Entity entity){

        Matrix4f matrix4f = UtilMath.createTransformationMatrix(
                entity.position,
                entity.rotation,
                entity.scale);

        shader.loadTransformMatrix(matrix4f);
    }

    //desenha o objeto
    public void draw(List<TexturedModel> tms){

        prepareObject(tms.get(0));

        for(TexturedModel tm: tms){

            prepareInstance(tm.getEntity());
            glDrawElements(GL_TRIANGLES, tm.getCountVertex(), GL_UNSIGNED_INT, 0);

        }
        umbindObject();

        for(TexturedModel tm: tms){
            tm.getEntity().increaseRotation(new float[]{.1f, .2f, .3f});
        }
    }

    //desativa objeto
    public void umbindObject(){

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
}
