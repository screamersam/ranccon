package br.com.screamer.opengl3initialrancoon.shader;

import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform3fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES30.GL_FRAGMENT_SHADER;
import static android.opengl.GLES30.GL_LINK_STATUS;
import static android.opengl.GLES30.GL_VALIDATE_STATUS;
import static android.opengl.GLES30.GL_VERTEX_SHADER;
import static android.opengl.GLES30.glAttachShader;
import static android.opengl.GLES30.glBindAttribLocation;
import static android.opengl.GLES30.glCompileShader;
import static android.opengl.GLES30.glCreateProgram;
import static android.opengl.GLES30.glCreateShader;
import static android.opengl.GLES30.glGetProgramInfoLog;
import static android.opengl.GLES30.glGetProgramiv;
import static android.opengl.GLES30.glLinkProgram;
import static android.opengl.GLES30.glShaderSource;
import static android.opengl.GLES30.glUniform1ui;
import static android.opengl.GLES30.glUseProgram;
import static android.opengl.GLES30.glValidateProgram;

import android.content.Context;
import android.renderscript.Float3;
import android.renderscript.Matrix4f;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class AbstractShader {

    private static int mProgramID;

    private static final String TAG = "AbstractShader";

    //construtores
    public AbstractShader(String vertex, String fragment) {

        mProgramID = createProgram(vertex, fragment);
    }

    public AbstractShader(Context context, int vertexResource, int fragmentResource) {

        mProgramID = createProgram(context, vertexResource, fragmentResource);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String handleName){

        glBindAttribLocation(mProgramID, attribute, handleName);
    }

    /*
     * utilitario para criar um programa de shader parametros String
     * */
    private int createProgram(String vertexResource, String fragmentResource){

        mProgramID = glCreateProgram();
        Log.i(TAG, "createProgram: "+mProgramID);

        int vertexID = loadShader(GL_VERTEX_SHADER, vertexResource);
        int fragmentID = loadShader(GL_FRAGMENT_SHADER, fragmentResource);

        glAttachShader(mProgramID, vertexID);
        glAttachShader(mProgramID, fragmentID);

        bindAttributes();

        glLinkProgram(mProgramID);

        final int[] linkStatus = new int[1];
        glGetProgramiv(mProgramID, GL_LINK_STATUS, linkStatus, 0);
        Log.v(TAG, "Results of linking codes: " + linkStatus[0]
                +"\nLog:" + glGetProgramInfoLog(mProgramID)
        );

        glValidateProgram(mProgramID);
        final int[] validateStatus = new int[1];
        glGetProgramiv(mProgramID, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + glGetProgramInfoLog(mProgramID));

        getAllUniformLocations();

        return mProgramID;
    }

    /*
     * utilitario para criar um programa de shader paramentros contexto e resourceCodes
     * */
    private int createProgram(Context c, int vertexResource, int fragmentResource){

       String v = readFile(c, vertexResource);
       String f = readFile(c, fragmentResource);
       return createProgram(v, f);
    }

    /*
     * ultilitario para criar um tipo de shader
     * */
    private static int loadShader(int type, String code){

        int mID = glCreateShader(type);

        glShaderSource(mID, code);

        glCompileShader(mID);

        Log.i(TAG, "loadShader: "+glGetShaderInfoLog(mID));

        return mID;
    }

    /*
     * ultilitario para criar um tipo de shader
     * */
    private static int loadShader(Context c, int type, int codeResource){

        String strShader = readFile(c, codeResource);

        return loadShader(type, strShader);
    }

    //liga programa
    public void onProgram(){

        glUseProgram(mProgramID);
    }

    //desliga programa
    public void offProgram(){

        glUseProgram(0);
    }

    public int getProgram() {

        return mProgramID;
    }

    /*
     * ultilitario para lêr arquivo
     * */
    public static String readFile(Context ctx, int rsrcId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(rsrcId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine;
        StringBuilder body = new StringBuilder();

        try
        {
            while((nextLine = bufferedReader.readLine()) != null)
            {
                body.append(nextLine);
                body.append('\n');
            }
        }
        catch(IOException e)
        {
            return null;
        }

        return body.toString();
    }

    protected abstract void getAllUniformLocations();

    public int getUniformLocation(String handleLocation){

        int uniform = glGetUniformLocation(mProgramID, handleLocation);
        if(uniform == -1){
            Log.i(TAG, "getUniformLocation: error: program: "+mProgramID+" handleL: "+handleLocation);
        }
        return uniform;
    }

    public void loadFloat(int location, float f){

        glUniform1f(location, f);
    }

    public void loadVector(int location, float[] f){

        glUniform3fv(location, 1, f, 0);
    }

    public void loadVector(int location, Float3 f){

        glUniform3fv(location, 1, new float[]{f.x, f.y, f.z}, 0);
    }

    FloatBuffer temp;

    public void loadMatrix(int location, Matrix4f matrix){

        temp = ByteBuffer.allocateDirect(16 * Float.BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        temp.put(matrix.getArray());
        temp.position(0);

        glUniformMatrix4fv(location, 1, false, temp);
    }

    public void loadBoolean(int location, boolean b){

        glUniform1ui(location, b? 1: 0);
    }
}
