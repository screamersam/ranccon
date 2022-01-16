package br.com.screamer.opengl3initialrancoon;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import br.com.screamer.opengl3initialrancoon.model.MyModel3D;
import br.com.screamer.opengl3initialrancoon.util.LoaderManagerObject;
import br.com.screamer.opengl3initialrancoon.util.UtilMath;

public class MainActivity extends Activity implements GLSurfaceView.Renderer{

    private GLSurfaceView glSurfaceView;

    private boolean renderSet = false;

    private LoaderManagerObject loaderManagerObject = new LoaderManagerObject();
    private MyModel3D mm3D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(3);
        glSurfaceView.setRenderer(this);

        getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        renderSet = true;
        //glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(renderSet){

            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(renderSet){

            glSurfaceView.onResume();

//            while (renderSet){
//
//                glSurfaceView.requestRender();
//            }
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mm3D = new MyModel3D(getBaseContext(), loaderManagerObject, R.raw.bunny, R.drawable.bunny);
        //mm3D.getEntity().scale = new float[]{.1f, .1f, .1f};
        mm3D.getEntity().scale = new float[]{1f, 1f, 1f};
        mm3D.getEntity().position = new float[]{0f, -5f, -15f};
        mm3D.getTexturedModel().setReflectivity(.1f);
        mm3D.getTexturedModel().setShineDamper(.5f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {

        android.opengl.GLES30.glViewport(0, 0, w, h);

        UtilMath.createProjectionMatrix(w, h);

        mm3D.loadProjection();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        mm3D.render();
    }
}