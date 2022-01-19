package br.com.screamer.opengl3initialrancoon.util;

/*
* classe responsavel por objeto no opengl
* */

import static android.opengl.GLES20.glDeleteBuffers;

import java.util.ArrayList;
import java.util.List;

public class MapArrayObj {

    public String name;

    public int countVertex;

    public int[] VAO;

    public int[] EBO;

    public List<int[]> VBOs = new ArrayList<>();

    public MapArrayObj(String name) {
        this.name = name;
    }

    public MapArrayObj(int[] VAO, int[] EBO, List<int[]> VBO) {
        this.VAO = VAO;
        this.EBO = EBO;
        this.VBOs = VBO;
    }

    public MapArrayObj(String name, int[] VAO, int[] EBO, List<int[]> VBO) {
        this.name = name;
        this.VAO = VAO;
        this.EBO = EBO;
        this.VBOs = VBO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getVAO() {
        return VAO;
    }

    public void setVAO(int[] VAO) {
        this.VAO = VAO;
    }

    public int[] getEBO() {
        return EBO;
    }

    public void setEBO(int[] EBO) {
        this.EBO = EBO;
    }

    public List<int[]> getVBOs() {
        return VBOs;
    }

    public void setVBOs(List<int[]> VBOs) {
        this.VBOs = VBOs;
    }

    public void deleteVBO(){

        VBOs.forEach(e->{
            glDeleteBuffers(1, e,0);
        });
    }

    public void deleteEBO(){

        glDeleteBuffers(1, EBO,0);
    }
}
