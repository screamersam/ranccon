package br.com.screamer.opengl3initialrancoon.enitity;

public class Entity {

    public float[] position = new float[]{0,0,0};
    public float[] rotation = new float[]{0,0,0};
    public float[] scale = new float[]{1,1,1};

    public Entity() {

    }

    public Entity(float[] position) {

        this.position = position;
    }

    public Entity(float[] position, float[] rotation, float[] scale) {

        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    //get positions
    public float getTX() {

        return position[0];
    }
    
    public float getTY() {

        return position[1];
    }

    public float getTZ() {

        return position[2];
    }

    //get rotations
    public float getRX() {
        return rotation[0];
    }

    public float getRY() {
        return rotation[1];
    }

    public float getRZ() {
        return rotation[2];
    }

    //get scales
    public float getSX() {
        return scale[0];
    }

    public float getSY() {
        return scale[1];
    }

    public float getSZ() {
        return scale[2];
    }


    //set positions
    public void setX(float a) {

        position[0] = a;
    }

    public void setY(float a) {

        position[1] = a;
    }

    public void setZ(float a) {

        position[2] = a;
    }

    //set rotations
    public void setRX(float a) {

        rotation[0] = a;
    }

    public void setRY(float a) {

        rotation[1] = a;
    }

    public void setRZ(float a) {

        rotation[2] = a;
    }

    //set scales
    public void setSX(float a) {

        scale[0] = a;
    }

    public void setSY(float a) {

        scale[1] = a;
    }

    public void setSZ(float a) {

        scale[2] = a;
    }

    public void increasePosition(float[] p){

        for (int i = 0; i < p.length; i++) {

            position[i] += p[i];
        }
    }

    public void increaseRotation(float[] r){

        for (int i = 0; i < r.length; i++) {

            rotation[i] += r[i];
        }
    }

    public void increaseScale(float[] s){

        for (int i = 0; i < s.length; i++) {

            scale[i] += s[i];
        }
    }
}