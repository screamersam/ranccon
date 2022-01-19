package br.com.screamer.opengl3initialrancoon.model;

import java.io.Serializable;

public class TriangleMesh3D implements Serializable {

    private int[] indices;
    private float[] colors;
    private float[] vertices;
    private float[] coordTextures;
    private float[] normals;

    private boolean hasColor = false;
    private boolean hasTextureCoords = false;
    private boolean hasNormals = false;

    public TriangleMesh3D(int[] indices, float[] colors, float[] vertices) {

        this.indices = indices;
        this.colors = colors;
        this.vertices = vertices;

        hasColor = true;
    }

    public TriangleMesh3D(int[] indices, float[] vertices, float[] coordTextures, float[] normals) {

        this.indices = indices;
        this.vertices = vertices;
        this.coordTextures = coordTextures;
        this.normals = normals;

        hasTextureCoords = true;
        hasNormals = true;
    }

    public TriangleMesh3D(int[] indices, float[] colors, float[] vertices, float[] coordTextures, float[] normals) {

        this.indices = indices;
        this.colors = colors;
        this.vertices = vertices;
        this.coordTextures = coordTextures;
        this.normals = normals;

        hasColor = true;
        hasTextureCoords = true;
        hasNormals = true;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getColors() {
        return colors;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getCoordTextures() {
        return coordTextures;
    }

    public float[] getNormals() {
        return normals;
    }

    public boolean isHasColor() {
        return hasColor;
    }

    public boolean isHasTextureCoords() {
        return hasTextureCoords;
    }

    public boolean isHasNormals() {
        return hasNormals;
    }
}
