package br.com.screamer.opengl3initialrancoon.model;

public class RawModel {

    public static final String TAG = "RawModel";
    private int id;
    private int countVertex;

    public RawModel(int[] id, int countVertex) {

        this.id = id[0];
        this.countVertex = countVertex;
    }

    public RawModel(int id, int countVertex) {

        this.id = id;
        this.countVertex = countVertex;
    }

    public int getId() {
        return id;
    }

    public int getCountVertex() {
        return countVertex;
    }
}
