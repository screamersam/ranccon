package br.com.screamer.opengl3initialrancoon.util;

import static android.opengl.GLES30.GL_ARRAY_BUFFER;
import static android.opengl.GLES30.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_STATIC_DRAW;
import static android.opengl.GLES30.glBindBuffer;
import static android.opengl.GLES30.glBindVertexArray;
import static android.opengl.GLES30.glBufferData;
import static android.opengl.GLES30.glDeleteBuffers;
import static android.opengl.GLES30.glDeleteVertexArrays;
import static android.opengl.GLES30.glGenBuffers;
import static android.opengl.GLES30.glGenVertexArrays;
import static android.opengl.GLES30.glVertexAttribPointer;
import static br.com.screamer.opengl3initialrancoon.util.UtilMenManager.getIntBuffer;
import static br.com.screamer.opengl3initialrancoon.util.UtilMenManager.getShortBuffer;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import br.com.screamer.opengl3initialrancoon.model.RawModel;

/*
* classe gerenciadora de objetos simples
*
**/

public class LoadRawObject implements Serializable {

    private HashMap<String, MapArrayObj> vaoList =  new HashMap<>();

    private MapArrayObj selected;

    private static int numberVAO = 0;

    //salvar e recuperar instancias
    public LoadRawObject() {

    }

    private int[] createVAO(){

        String name = "vbo"+numberVAO++;

        selected = new MapArrayObj(name);

        int[] vaoID = new int[1];
        glGenVertexArrays(1, vaoID, 0);
        vaoList.put(name, selected);
        glBindVertexArray(vaoID[0]);
        selected.setVAO(vaoID);
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

    //attributo
    private void storeDataAttribList(int attribNumber, int elementsSize, float[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.getVBOs().add(arrayBufferID);

        glBindBuffer(GL_ARRAY_BUFFER, arrayBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.length * Float.BYTES, UtilMenManager.getFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, elementsSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //attributo
    private void storeDataAttribList(int attribNumber, int elementsSize, FloatBuffer data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.getVBOs().add(arrayBufferID);

        glBindBuffer(GL_ARRAY_BUFFER, arrayBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.limit() * Float.BYTES, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, elementsSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //attributo 3D
    private void storeDataAttribList(int attribNumber, float[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.getVBOs().add(arrayBufferID);

        glBindBuffer(GL_ARRAY_BUFFER, arrayBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, data.length * Float.BYTES, UtilMenManager.getFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //indice de atributos short
    public ShortBuffer storeIndices(short[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.EBO = arrayBufferID;

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, arrayBufferID[0]);
        ShortBuffer sb = getShortBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.length * Short.BYTES, sb, GL_STATIC_DRAW);
        return sb;
    }

    //indice de atributos int
    public IntBuffer storeIndices(int[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.EBO = arrayBufferID;

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, arrayBufferID[0]);
        IntBuffer ib = getIntBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.length * Integer.BYTES, ib, GL_STATIC_DRAW);
        return ib;
    }

    public void save(){

    }

    public void load(){

    }

    public void clear(){

        vaoList.values().forEach(s->{
            glDeleteBuffers(1, s.EBO, 0);
            s.getVBOs().forEach(vbo->{

                glDeleteBuffers(1, vbo, 0);
            });
            glDeleteVertexArrays(1, s.VAO,0);
        });
    }


}
