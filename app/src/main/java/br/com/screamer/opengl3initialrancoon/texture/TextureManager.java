package br.com.screamer.opengl3initialrancoon.texture;

import static android.opengl.GLES30.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES30.GL_NEAREST;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES30.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glGenTextures;
import static android.opengl.GLES30.glTexParameteri;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;

import java.util.HashMap;

public class TextureManager {

    private static int countTexture = 0;

    private HashMap<String, Texture> textures = new HashMap<>();

    public TextureManager(){

    }

    public int[] newTexture(){

        int[] iTextureId = new int[1];
        glGenTextures(1, iTextureId, 0);
        textures.put("texture_"+countTexture++, new Texture(iTextureId));
        return iTextureId;
    }

    public int[] newTexture(String textureName){

        int[] iTextureId = new int[1];
        glGenTextures(1, iTextureId, 0);
        textures.put(textureName, new Texture(iTextureId));
        return iTextureId;
    }

    public int[] newTexture(Bitmap bitmap){

        int[] iTextureId = newTexture();

        loadTextureToOpenGL(iTextureId, bitmap);

        return iTextureId;
    }


    public int[] newTexture(String textureName, Bitmap bitmap){

        int[] iTextureId = newTexture(textureName);

        loadTextureToOpenGL(iTextureId, bitmap);

        return iTextureId;
    }

    /*
    * auxiliar que carrega textura ao OpenGL
    * */
    public static int[] loadTextureToOpenGL(int[] iTextureId, Bitmap bitmap){

        glBindTexture(GL_TEXTURE_2D, iTextureId[0]);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        return iTextureId;
    }

    /*
    * auxiliar que carrega um resourceImageFile
    * */
    public static Bitmap loadBitmap(Context c, int resourceImage){

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(c.getResources(), resourceImage,
                options);
        Matrix matrix = new Matrix();
        //matrix.preScale(1f,-1f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public void texturesStatus(){

        textures.keySet().forEach(e->{

            System.out.println(e);
        });
    }

    public HashMap<String, Texture> getTextures() {
        return textures;
    }

    public Texture getTexture(String key){

        return textures.get(key);
    }
}
