package br.com.screamer.opengl3initialrancoon.util;

import android.renderscript.Matrix4f;

import br.com.screamer.opengl3initialrancoon.camera.Camera;

/*
* classe com utilitarios matemáticos
* */

public class UtilMath {

    static float FV = 80f;
    static float near = 0.001f;
    static float far = 1000f;

    public static Matrix4f projectionMatrix;

    public static String TAG = "Math";

    public static Matrix4f createProjectionMatrix(float width, float height){

        float aspectRatio = width / height;
//        double radianAngle = java.lang.Math.toRadians(FV/2f);
//        double radianAngle2 = java.lang.Math.tan(1f/ radianAngle);
//        double radianAngle3 = radianAngle2 * aspectRatio;
//        float scaleY = (float) radianAngle3;
//        float scaleX = (float) radianAngle3 / aspectRatio;
//        float frustum = far - near;

        projectionMatrix = new Matrix4f();
        projectionMatrix.loadIdentity();
        //projectionMatrix.loadOrtho(0, width,height, 0, near, far);
        projectionMatrix.loadPerspective(FV, aspectRatio, near, far);
        //projectionMatrix.loadFrustum( 0, aspectRatio, aspectRatio, -.2f, near, far);
        return projectionMatrix;
    }

    public static Matrix4f createTransformationMatrix(float[] translation, float[] rotation, float[] scale){

        Matrix4f matrix4f = new Matrix4f();
        matrix4f.translate(translation[0],translation[1],translation[2]);
        matrix4f.rotate(rotation[0], 1,0,0);
        matrix4f.rotate(rotation[1], 0,1,0);
        matrix4f.rotate(rotation[2], 0,0,1);
        matrix4f.scale(scale[0],scale[1],scale[2]);
        return matrix4f;
    }

    public static Matrix4f createViewMatrix(Camera camera){

        Matrix4f matrix4f = new Matrix4f();
        //Log.i(TAG, "createViewMatrix: init " + Arrays.toString(matrix4f.getArray()));
        matrix4f.rotate((float) Math.toRadians(camera.getPitch()), 1,0,0);
        //Log.i(TAG, "createViewMatrix: rotate " + Arrays.toString(matrix4f.getArray()));
        matrix4f.rotate((float) Math.toRadians(camera.getYaw()), 0, 1, 0);
        //Log.i(TAG, "createViewMatrix: ");
        matrix4f.inverse();
        //Log.i(TAG, "createViewMatrix: inverse" + Arrays.toString(matrix4f.getArray()));
        matrix4f.translate(camera.getPosition()[0], camera.getPosition()[1], camera.getPosition()[2]);
        //Log.i(TAG, "createViewMatrix: translate" + Arrays.toString(matrix4f.getArray()));
        return matrix4f;
    }
}
