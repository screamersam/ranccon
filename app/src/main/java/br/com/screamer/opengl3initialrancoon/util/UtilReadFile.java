package br.com.screamer.opengl3initialrancoon.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Float2;
import android.renderscript.Float3;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import br.com.screamer.opengl3initialrancoon.model.TriangleMesh3D;

/*
* classe com utilitarios de leitura
* */

public class UtilReadFile {

    public static String TAG = "Leitor";

    public static int countLines(Context context, int resourceFile){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        int countLine = 0;

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                countLine++;
            }
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return countLine;
    }

    public static int countVertices(Context context, int resourceFile){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        int countVertices = 0;

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                if(nextLine.startsWith("v ")){

                    countVertices++;
                }
            }
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return countVertices;
    }

    public static int countTextures(Context context, int resourceFile){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        int countTextures = 0;

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                if(nextLine.startsWith("vt ")){

                    countTextures++;
                }
            }
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return countTextures;
    }

    public static int countNormals(Context context, int resourceFile){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        int countNormals = 0;

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                if(nextLine.startsWith("vn ")){

                    countNormals++;
                }
            }
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return countNormals;
    }

    public static int countFaces(Context context, int resourceFile){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        int countFaces = 0;

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                if(nextLine.startsWith("f ")){

                    countFaces++;
                }
            }
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return countFaces;
    }

    public static String leituraArquivo(Context context, int resourceFile, String regex){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        StringBuilder stb = new StringBuilder();

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                if(nextLine.matches(regex)){
                    stb.append(nextLine);
                }
            }
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return stb.toString();
    }

    public static void saveFile(String data, String nameFile, Context context){

        try {
            Log.i(TAG, "saving file...");
            FileOutputStream fos = context.openFileOutput(nameFile, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
            Log.i(TAG, "File: "+nameFile+" saved!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readObj3D(Context context, int resourceFile, ArrayList<Float3> positions, ArrayList<Float2> textures, ArrayList<Float3> normals, ArrayList<PTN> ptns

    ){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceFile)));

        StringBuilder stb = new StringBuilder();

        String nextLine = null;

        try{

            while((nextLine = bufferedReader.readLine()) != null){

                if(nextLine.startsWith("v ")){

                    Float3 vertice = lineToVector3D(nextLine, "v ".length());
                    positions.add(vertice);
                } else

                if(nextLine.startsWith("vt ")){

                    Float2 texture = lineToVector2D(nextLine, "vt ".length());
                    textures.add(texture);
                } else

                if(nextLine.startsWith("vn ")){

                    Float3 normal = lineToVector3D(nextLine, "vn ".length());
                    normals.add(normal);
                } else

                if(nextLine.startsWith("f ")){

                    PTN face = intFacesPTN(nextLine, "f ".length());
                    ptns.add(face);
                }
            }

            String verticeCount = String.format("quantidade de vertices: %d\n", positions.size());
            String textureCoordCount = String.format("quantidade de coordenadas de textura: %d\n", textures.size());
            String normalCount = String.format("quantidade de normals: %d\n", normals.size());
            String facesCount = String.format("quantidade de triangulos: %d\n", ptns.size());

            stb.append(verticeCount);
            stb.append(textureCoordCount);
            stb.append(normalCount);
            stb.append(facesCount);

            saveFile(stb.toString(), "loadObj.log", context);
        }

        catch(IOException e){

            throw new RuntimeException("Error loading obj file");
        }

        return stb.toString();
    }

    public static Float3 lineToVector3D(String s, int initSubString){

        String[] temp = s.substring(initSubString).trim().split(" ");
        return new Float3(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]), Float.parseFloat(temp[2]));
    }

    public static Float2 lineToVector2D(String s, int initSubString){

        String[] temp = s.substring(initSubString).split(" ");
        return new Float2(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]));
    }

    //position, texture, normal
    public static PTN facesPTN(String s, int initSubString){

        String[] temp = s.substring(initSubString).split(" ");

        String[] v1 = temp[0].split("/");
        String[] v2 = temp[1].split("/");
        String[] v3 = temp[2].split("/");

        if(Short.parseShort(v1[0]) == Short.MAX_VALUE){

            throw new RuntimeException("Limite short de "+Short.MAX_VALUE +" alcançado!");
        }

        Face position = new Face(Short.parseShort(v1[0]), Short.parseShort(v2[0]), Short.parseShort(v3[0]));
        Face texture  = new Face(Short.parseShort(v1[1]), Short.parseShort(v2[1]), Short.parseShort(v3[1]));
        Face normal   = new Face(Short.parseShort(v1[2]), Short.parseShort(v2[2]), Short.parseShort(v3[2]));

        return new PTN(position, texture, normal);
    }

    //int indice position, texture, normal
    public static PTN intFacesPTN(String s, int initSubString){

        String[] temp = s.substring(initSubString).split(" ");

        String[] v1 = temp[0].split("/");
        String[] v2 = temp[1].split("/");
        String[] v3 = temp[2].split("/");

        if(Integer.parseInt(v1[0]) == Integer.MAX_VALUE){

            throw new RuntimeException("Limite short de "+Short.MAX_VALUE +" alcançado!");
        }

        Face position = new Face(Integer.parseInt(v1[0]), Integer.parseInt(v2[0]), Integer.parseInt(v3[0]));
        Face texture  = new Face(Integer.parseInt(v1[1]), Integer.parseInt(v2[1]), Integer.parseInt(v3[1]));
        Face normal   = new Face(Integer.parseInt(v1[2]), Integer.parseInt(v2[2]), Integer.parseInt(v3[2]));

        return new PTN(position, texture, normal);
    }


    //deve retornar um mesh
    public static MapArrayObj mount(OpenGLTexturedObject loaderManagerObject, Bitmap bitmap, ArrayList<PTN> lfs,
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

        for (PTN ptn: lfs){

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

    public static TriangleMesh3D getTriangleMesh3D(ArrayList<PTN> lfs,
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

        for (PTN ptn: lfs){

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

        return new TriangleMesh3D(indices, iPositions, itextures, inormals);
    }

    public static class ShortFace extends Face{

        public short v1, v2, v3;

        public ShortFace(short v1, short v2, short v3) {

            super(v1, v2, v3);
        }
    }

    public static class Face{

        public int v1, v2, v3;

        public Face(int v1, int v2, int v3) {

            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
        }
    }

    public static class PTN{

        public Face position, texture, normal;

        public PTN() {

        }

        public PTN(Face position, Face texture, Face normal) {

            this.position = position;
            this.texture = texture;
            this.normal = normal;
        }
    }
}
