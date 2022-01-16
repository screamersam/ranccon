package br.com.screamer.opengl3initialrancoon.camera;

public class Camera {

    private float[] position = new float[3];

    private float pitch;
    private float yaw;
    private float roll;

    public void move(float[] v){

        for (int i = 0; i < position.length; i++) {

            position[i] = v[i];
        }
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
