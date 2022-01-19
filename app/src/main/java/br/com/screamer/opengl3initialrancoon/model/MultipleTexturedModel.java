package br.com.screamer.opengl3initialrancoon.model;

import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;

import br.com.screamer.opengl3initialrancoon.shader.StaticShader;

public class MultipleTexturedModel {

    private StaticShader shader;
    private List<RenderTexturedModel3D> texturedModels = new ArrayList<>();

    public MultipleTexturedModel(StaticShader shader) {

        this.shader = shader;
    }

//    public void setTexturedModels(List<TexturedModel> texturedModels) {
//        this.texturedModels = texturedModels;
//    }
//
//    public TexturedModel getTexturedModel(int index) {
//
//        return texturedModels.get(index);
//    }
//
//    public void initProjection(int width, int height){
//
//        UtilMath.projectionMatrix = UtilMath.createProjectionMatrix(width, height);
//        shader.onProgram();
//        shader.loadProjectionMatrix(UtilMath.projectionMatrix);
//        shader.offProgram();
//    }
//
//    public void prepare(){
//
//        glEnable(GL_DEPTH_TEST);
//        glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);
//        glClearColor(0f,0f,0f,1f);
//    }
//
//    public void draw(Light sun, Camera camera){
//
//        prepare();
//        shader.onProgram();
//        shader.loadLight(sun);
//        shader.loadViewMatrix(camera);
//        draw(texturedModels);
//        shader.offProgram();
//        //limpar entidades
//    }
//
//    //ativa objeto
//    public void prepareObject(TexturedModel tm){
//
//        glBindVertexArray(tm.getId());
//
//        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);
//        glEnableVertexAttribArray(2);
//
//        //TextureManager.setTexture(tm.getTexture().getTextureId());
//        glActiveTexture(GL_TEXTURE0);
//        glBindTexture(0,tm.getTextureManager().getTextureId("texture_0")[0]);
//
//        shader.loadShineData(tm.getShineDamper(), tm.getReflectivity());
//    }
//
//    public void prepareInstance(Entity entity){
//
//        Matrix4f matrix4f = UtilMath.createTransformationMatrix(
//                entity.position,
//                entity.rotation,
//                entity.scale);
//
//        shader.loadTransformMatrix(matrix4f);
//    }

//    //desenha o objeto
//    public void draw(List<TexturedModel> tms){
//
//        prepareObject(tms.get(0));
//
//        for(TexturedModel tm: tms){
//
//            prepareInstance(tm.getEntity());
//            glDrawElements(GL_TRIANGLES, tm.getCountVertex(), GL_UNSIGNED_INT, 0);
//
//        }
//        umbindObject();
//
//        for(TexturedModel tm: tms){
//            tm.getEntity().increaseRotation(new float[]{.1f, .2f, .3f});
//        }
//    }

    //desativa objeto
    public void umbindObject(){

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
}
