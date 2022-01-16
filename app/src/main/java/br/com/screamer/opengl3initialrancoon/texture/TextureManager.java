package br.com.screamer.opengl3initialrancoon.texture;

import static android.opengl.GLES30.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES30.GL_NEAREST;
import static android.opengl.GLES30.GL_REPEAT;
import static android.opengl.GLES30.GL_RGB;
import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES30.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES30.GL_UNSIGNED_BYTE;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glGenTextures;
import static android.opengl.GLES30.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;

import java.util.HashMap;

import br.com.screamer.opengl3initialrancoon.R;

public class TextureManager {

    private static int countTexture = 0;

    private HashMap<String, int[]> textureID = new HashMap<>();

    public TextureManager(){

        textureID.put("texture_"+countTexture++, newTexture());
    }

    public TextureManager(Context c){

        textureID.put("texture_"+countTexture++, loadTextureToOpenGL(c, R.drawable.bobesponja));
    }

    public int[] getTextureId(String s) {

        return textureID.get(s);
    }

    public static void setTexture(int iTextureId){

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, iTextureId);
    }

    public static int[] newTexture(){

        int[] iTextureId = new int[1];
        glGenTextures(1, iTextureId, 0);
        return iTextureId;
    }

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

    public static Bitmap loadBitmap(Context c, int resourceImage){

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(c.getResources(), resourceImage,
                options);
        Matrix matrix = new Matrix();
        //matrix.preScale(1f,-1f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public int[] loadTextureToOpenGL(Context ctx, int rsrcId){

        int[] iTextureId = new int[1];
        glActiveTexture(GL_TEXTURE0);
        glGenTextures(1, iTextureId, 0);

        if(iTextureId[0] != 0)
        {
            glBindTexture(GL_TEXTURE_2D, iTextureId[0]);

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inScaled = false;

            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), rsrcId,
                    options);
//            Matrix matrix = new Matrix();
//            matrix.preScale(1f,-1f);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
            texImage2D(GL_TEXTURE_2D, 0, GL_RGB, bitmap, GL_UNSIGNED_BYTE, 0);
            bitmap.recycle();

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        }
        else
        {
            throw new RuntimeException("Error loading texture");
        }

        return iTextureId;
    }

    public void texturesStatus(){

        textureID.keySet().forEach(e->{

            System.out.println(e);
        });
    }

    public void clear(){

        textureID.clear();
    }
}
