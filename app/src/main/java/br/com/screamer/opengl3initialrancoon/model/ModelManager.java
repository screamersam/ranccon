package br.com.screamer.opengl3initialrancoon.model;

import android.content.Context;
import android.renderscript.Float2;
import android.renderscript.Float3;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.screamer.opengl3initialrancoon.util.MapArrayObj;
import br.com.screamer.opengl3initialrancoon.util.OpenGLTexturedObject;
import br.com.screamer.opengl3initialrancoon.util.UtilReadFile;

/*
* classe gerenciadora de objetos
* */
public class ModelManager extends OpenGLTexturedObject{

    private Context context;
    private HashMap<String, MapArrayObj> models;

    public ModelManager(Context context) {

        models = new HashMap<>();
        this.context = context;
    }

    public MapArrayObj newModel(String name, int resourceObjectFile) {

        ArrayList<Float3> lVertexs = new ArrayList<>();
        ArrayList<Float2> lTextureCoords = new ArrayList<>();
        ArrayList<Float3> lNormals = new ArrayList<>();
        ArrayList<UtilReadFile.PTN> PTNS = new ArrayList<>();

        //converte arquivo .obj em indices, vertices, coordenadas de texturas e normals
        UtilReadFile.readObj3D(context, resourceObjectFile, lVertexs, lTextureCoords, lNormals, PTNS);

        //carrega .obj em indices, vertices, coordenadas de texturas e normals no opengl
        // retornando uma instancia AObj
        MapArrayObj mapArrayObj = getAObj(this, PTNS, lVertexs, lTextureCoords, lNormals);
        models.put(name, mapArrayObj);
        return mapArrayObj;
    }

    public MapArrayObj getModel(String name){

        return models.get(name);
    }

    //Vincula buffers de vertices coordTexturas e normals ao OpenGL retornando uma instancia AObj
    public static MapArrayObj getAObj(OpenGLTexturedObject loaderManagerObject, ArrayList<UtilReadFile.PTN> lfs,
                                      ArrayList<Float3> positions, ArrayList<Float2> textures, ArrayList<Float3> normals
    ){

        if(lfs.size() >= Integer.MAX_VALUE){
            //erro indices muito grande
            throw new RuntimeException("Erro, indices maior que short");
        }

        int[] indices = new int[lfs.size() * 3];
        float[] iPositions = new float[lfs.size() * 3 * 3];
        float[] itextures = new float[lfs.size() * 3 * 2];
        float[] inormals = new float[lfs.size() * 3 * 3];

        int countElements = 0;

        for (UtilReadFile.PTN ptn: lfs){

            indices[countElements * 3] = (int) (countElements * 3);
            indices[countElements * 3 + 1] = (int) (countElements * 3 + 1);
            indices[countElements * 3 + 2] = (int) (countElements * 3 + 2);

            Float3 p1 = positions.get(ptn.position.v1 -1);
            Float3 p2 = positions.get(ptn.position.v2 -1);
            Float3 p3 = positions.get(ptn.position.v3 -1);

            iPositions[countElements * 9] = p1.x;
            iPositions[countElements * 9 +1] = p1.y;
            iPositions[countElements * 9 +2] = p1.z;

            iPositions[countElements * 9 +3] = p2.x;
            iPositions[countElements * 9 +4] = p2.y;
            iPositions[countElements * 9 +5] = p2.z;

            iPositions[countElements * 9 +6] = p3.x;
            iPositions[countElements * 9 +7] = p3.y;
            iPositions[countElements * 9 +8] = p3.z;

            Float2 t1 = textures.get(ptn.texture.v1 -1);
            Float2 t2 = textures.get(ptn.texture.v2 -1);
            Float2 t3 = textures.get(ptn.texture.v3 -1);

            itextures[countElements * 6] = t1.x;
            itextures[countElements * 6 +1] = 1- t1.y;;

            itextures[countElements * 6 +2] = t2.x;
            itextures[countElements * 6 +3] = 1- t2.y;;

            itextures[countElements * 6 +4] = t3.x;
            itextures[countElements * 6 +5] = 1- t3.y;;

            Float3 n1 = normals.get(ptn.normal.v1 -1);
            Float3 n2 = normals.get(ptn.normal.v2 -1);
            Float3 n3 = normals.get(ptn.normal.v3 -1);

            inormals[countElements * 9] = n1.x;
            inormals[countElements * 9 +1] = n1.y;;
            inormals[countElements * 9 +2] = n1.z;;

            inormals[countElements * 9 +3] = n2.x;
            inormals[countElements * 9 +4] = n2.y;;
            inormals[countElements * 9 +5] = n2.z;;

            inormals[countElements * 9 +6] = n3.x;
            inormals[countElements * 9 +7] = n3.y;;
            inormals[countElements * 9 +8] = n3.z;;

            countElements++;
        }

        return loaderManagerObject.loadTexturedModel(indices, iPositions, itextures, inormals);
    }
}
