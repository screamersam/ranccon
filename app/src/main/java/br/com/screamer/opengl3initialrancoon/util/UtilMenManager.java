package br.com.screamer.opengl3initialrancoon.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class UtilMenManager {

    public static FloatBuffer getFloatBuffer(float[] data){
        FloatBuffer fb = ByteBuffer.allocateDirect(data.length * Float.BYTES)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(data).flip();
        return  fb;
    }

    public static ShortBuffer getShortBuffer(short[] data){
        ShortBuffer sb = ByteBuffer.allocateDirect(data.length * Short.BYTES)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
        sb.put(data).flip();
        return  sb;
    }

    public static IntBuffer getIntBuffer(int[] data){
        IntBuffer sb = ByteBuffer.allocateDirect(data.length * Integer.BYTES)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        sb.put(data).flip();
        return  sb;
    }


}
