package br.com.screamer.opengl3initialrancoon.shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import br.com.screamer.opengl3initialrancoon.camera.Camera;
import br.com.screamer.opengl3initialrancoon.R;
import br.com.screamer.opengl3initialrancoon.util.UtilMath;

public class SimpleMeshShader extends AbstractShader{

    private static final int verticeResource = R.raw.vertex_simple_mesh_code;
    private static final int fragmentResource = R.raw.fragment_simple_mesh_code;

    private int locationTransformMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    public SimpleMeshShader(Context context) {

        super(context, verticeResource, fragmentResource);
    }

    @Override
    protected void bindAttributes() {

        super.bindAttribute(0, "aPosition");
        super.bindAttribute(1, "aColor");
    }

    @Override
    protected void getAllUniformLocations() {

        locationTransformMatrix = super.getUniformLocation("uMatrix");
        locationProjectionMatrix = super.getUniformLocation("uProjection");
        locationViewMatrix = super.getUniformLocation("uViewMatrix");
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
}
