package br.com.screamer.opengl3initialrancoon.shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import br.com.screamer.opengl3initialrancoon.camera.Camera;
import br.com.screamer.opengl3initialrancoon.R;
import br.com.screamer.opengl3initialrancoon.util.Light;
import br.com.screamer.opengl3initialrancoon.util.UtilMath;

public class StaticShader extends AbstractShader {

    private static final int verticeResource = R.raw.vertex_code;
    private static final int fragmentResource = R.raw.fragment_code;

    private int locationTransformMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationShineDumper;
    private int locationReflectivity;

    public StaticShader(Context context) {

        super(context, verticeResource, fragmentResource);
    }

    @Override
    protected void bindAttributes() {

        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texturePosition");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {

        locationTransformMatrix = super.getUniformLocation("uMatrix");
        locationProjectionMatrix = super.getUniformLocation("uProjection");
        locationViewMatrix = super.getUniformLocation("uViewMatrix");
        locationLightPosition = super.getUniformLocation("uLightP");
        locationLightColor = super.getUniformLocation("uLightC");
        locationShineDumper = super.getUniformLocation("uShineDumper");
        locationReflectivity = super.getUniformLocation("uReflectivity");
    }

    public void loadTransformMatrix(Matrix4f matrix) {

        super.loadMatrix(locationTransformMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {

        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {

        super.loadMatrix(locationViewMatrix, UtilMath.createViewMatrix(camera));
    }

    public void loadLight(Light l) {

        super.loadVector(locationLightPosition, l.getPosition());
        super.loadVector(locationLightColor, l.getColor());
    }

    public void loadShineData(float shine, float reflectivity){

        super.loadFloat(locationShineDumper, shine);
        super.loadFloat(locationReflectivity, reflectivity);
    }
}
