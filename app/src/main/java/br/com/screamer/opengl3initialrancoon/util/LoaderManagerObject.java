package br.com.screamer.opengl3initialrancoon.util;

import static android.opengl.GLES30.*;

import static br.com.screamer.opengl3initialrancoon.util.UtilMenManager.getIntBuffer;
import static br.com.screamer.opengl3initialrancoon.util.UtilMenManager.getShortBuffer;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import br.com.screamer.opengl3initialrancoon.model.RawModel;
import br.com.screamer.opengl3initialrancoon.model.TexturedModel;
import br.com.screamer.opengl3initialrancoon.texture.TextureManager;

/*
* classe gerenciadora de objetos
*
**/

public class LoaderManagerObject implements Serializable {

    private static HashMap<String, int[]> vaoList =  new HashMap<>();
    private static HashMap<String, int[]> vboList =  new HashMap<>();
    private static HashMap<String, int[]> eboList =  new HashMap<>();

    private static int numberVAO = 0;
    private static int numberVBO = 0;
    private static int numberEBO = 0;

    //salvar e recuperar instancias
    public LoaderManagerObject() {

    }

    private int[] createVAO(){

        int[] vaoID = new int[1];
        glGenVertexArrays(1, vaoID, 0);
        vaoList.put("vbo"+numberVAO++, vaoID);
        glBindVertexArray(vaoID[0]);
        return vaoID;
    }

    //pouca aplicação
    //raw model 3D indexed
    public RawModel loadRawModel3D(short[] indices, float[] data){

        int[] vaoID = createVAO();
        storeIndices(indices);
        storeDataAttribList(0, 3, data);
        return new RawModel(vaoID, indices.length); //objeto 3D
    }

    //pouca aplicação
    //raw model 3D indexed RGB
    public RawModel loadRawModel3D(int[] indices, float[] data){

        int[] vaoID = createVAO();
        storeIndices(indices);
        storeDataAttribList(0, 3, data);
        return new RawModel(vaoID, indices.length); //objeto 3D
    }

    //rawmodel colored 2D RGB
    public RawModel loadColoredModel2D(int[] indices, float[] data, float[] color){

        int[] vaoID = createVAO();
        storeIndices(indices);
        storeDataAttribList(0, 2, data);
        storeDataAttribList(1, 3, color);
        return new RawModel(vaoID, indices.length); //objeto 3D
    }

    //rawmodel colored 3D
    public RawModel loadColoredModel3D(int[] indices, float[] data, float[] color){

        int[] vaoID = createVAO();
        storeIndices(indices);
        storeDataAttribList(0, 3, data);
        storeDataAttribList(1, 3, color);
        return new RawModel(vaoID, indices.length); //objeto 3D
    }

    //texturedModel 2D
    public TexturedModel loadTexturedModel(short[] indices, float[] positions, float[] texturePositions, Bitmap bitmap) {

        TextureManager texture = new TextureManager();

        RawModel rawModel = loadRawModel3D(indices, positions);

        storeDataAttribList(1, 2, texturePositions);

        TextureManager.loadTextureToOpenGL(texture.getTextureId("texture_0"), bitmap);

        TexturedModel tm = new TexturedModel(rawModel, texture);
        //tm.getEntity().position = new float[]{0, 0, -35};
        //tm.getEntity().scale = new float[]{.9f, .9f, .9f};
        return tm;
    }

    //texturedModel 2D
    public TexturedModel loadTexturedModel(int[] indices, float[] positions, float[] texturePositions, Bitmap bitmap) {

        TextureManager texture = new TextureManager();

        RawModel rawModel = loadRawModel3D(indices, positions);
        storeDataAttribList(1, 2, texturePositions);

        TextureManager.loadTextureToOpenGL(texture.getTextureId("texture_0"), bitmap);

        TexturedModel tm = new TexturedModel(rawModel, texture);
        //tm.getEntity().position = new float[]{0, 0, -35};
        //tm.getEntity().scale = new float[]{.9f, .9f, .9f};
        return tm;
    }

    //texturedModel 3D
    public TexturedModel loadTexturedModel(int[] indices, float[] positions, float[] texturePositions, float[] normals, Bitmap bitmap) {

        TextureManager texture = new TextureManager();

        RawModel rawModel = loadRawModel3D(indices, positions);
        storeDataAttribList(1, 2, texturePositions);
        storeDataAttribList(2, 3, normals);

        TextureManager.loadTextureToOpenGL(texture.getTextureId("texture_0"), bitmap);

        TexturedModel tm = new TexturedModel(rawModel, texture);
        return tm;
    }

    //attributo
    private void storeDataAttribList(int attribNumber, int elementsSize, float[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        vboList.put("vbo"+numberVBO++, arrayBufferID);

        glBindBuffer(GL_ARRAY_BUFFER, arrayBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.length * Float.BYTES, UtilMenManager.getFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, elementsSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //attributo
    private void storeDataAttribList(int attribNumber, int elementsSize, FloatBuffer data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        vboList.put("vbo"+numberVBO++, arrayBufferID);

        glBindBuffer(GL_ARRAY_BUFFER, arrayBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.limit() * Float.BYTES, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, elementsSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //attributo 3D
    private void storeDataAttribList(int attribNumber, float[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        vboList.put("vbo"+numberVBO++, arrayBufferID);

        glBindBuffer(GL_ARRAY_BUFFER, arrayBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.length * Float.BYTES, UtilMenManager.getFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //indice de atributos short
    public ShortBuffer storeIndices(short[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        eboList.put("ebo"+numberEBO++, arrayBufferID);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, arrayBufferID[0]);
        ShortBuffer sb = getShortBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.length * Short.BYTES, sb, GL_STATIC_DRAW);
        return sb;
    }

    //indice de atributos int
    public IntBuffer storeIndices(int[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        eboList.put("ebo"+numberEBO++, arrayBufferID);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, arrayBufferID[0]);
        IntBuffer ib = getIntBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.length * Integer.BYTES, ib, GL_STATIC_DRAW);
        return ib;
    }

    public void save(){

    }

    public void load(){

    }
}
