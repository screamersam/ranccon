package br.com.screamer.opengl3initialrancoon.util;

import static android.opengl.GLES30.*;

import static br.com.screamer.opengl3initialrancoon.util.UtilMenManager.getIntBuffer;
import static br.com.screamer.opengl3initialrancoon.util.UtilMenManager.getShortBuffer;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

/*
* classe gerenciadora de objetos texturizados
*
**/

public class OpenGLTexturedObject {

    private HashMap<String, MapArrayObj> vaoList =  new HashMap<>();

    private MapArrayObj selected;

    private static int numberVAO = 0;

    //salvar e recuperar instancias
    public OpenGLTexturedObject() {

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

    //texturedModel 2D
    public MapArrayObj loadTexturedModel(int[] indices, float[] positions, float[] texturePositions, float[] normals) {

        createVAO();

        storeDataAttribList(0, 3, positions);
        storeDataAttribList(1, 2, texturePositions);
        storeDataAttribList(2, 3, normals);

        storeIndices(indices);

        return selected;
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
    private ShortBuffer storeIndices(short[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.EBO = arrayBufferID;
        selected.countVertex = data.length;

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, arrayBufferID[0]);
        ShortBuffer sb = getShortBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.length * Short.BYTES, sb, GL_STATIC_DRAW);
        return sb;
    }

    //indice de atributos int
    private IntBuffer storeIndices(int[] data){

        int[] arrayBufferID = new int[1];
        glGenBuffers(1, arrayBufferID, 0);

        selected.EBO = arrayBufferID;
        selected.countVertex = data.length;

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, arrayBufferID[0]);
        IntBuffer ib = getIntBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.length * Integer.BYTES, ib, GL_STATIC_DRAW);
        return ib;
    }

    public void delete(MapArrayObj m){

        glDeleteBuffers(1, m.EBO, 0);
        m.getVBOs().forEach(vbo->{

            glDeleteBuffers(1, vbo, 0);
        });
        glDeleteVertexArrays(1, m.VAO,0);
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
