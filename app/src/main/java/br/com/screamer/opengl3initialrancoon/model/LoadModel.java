package br.com.screamer.opengl3initialrancoon.model;

import android.content.Context;
import android.renderscript.Float2;
import android.renderscript.Float3;

import java.util.ArrayList;

import br.com.screamer.opengl3initialrancoon.texture.TextureManager;
import br.com.screamer.opengl3initialrancoon.util.UtilReadFile;
import br.com.screamer.opengl3initialrancoon.util.LoaderManagerObject;

public class LoadModel {

    public static TexturedModel loadTexturedModel(Context context, int resourceFile, int resourceBitmap, LoaderManagerObject loaderManagerObject){

        ArrayList<UtilReadFile.PTN> ptn = new ArrayList<>();

        ArrayList<Float3> p = new ArrayList<>();
        ArrayList<Float2> t = new ArrayList<>();
        ArrayList<Float3> n = new ArrayList<>();

        UtilReadFile.readObj3D(context, resourceFile, p, t, n, ptn);
        return UtilReadFile.mount(loaderManagerObject, TextureManager.loadBitmap(context, resourceBitmap), ptn, p, t, n);
    }
}
