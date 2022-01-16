package br.com.screamer.opengl3initialrancoon.util;

import android.renderscript.Float3;

public class Light {

    private Float3 position = new Float3(30,30,30);
    private Float3 color = new Float3(1,1,1);

    public Light() {
    }

    public Light(Float3 position, Float3 color) {
        this.position = position;
        this.color = color;
    }

    public Float3 getPosition() {
        return position;
    }

    public void setPosition(Float3 position) {
        this.position = position;
    }

    public Float3 getColor() {
        return color;
    }

    public void setColor(Float3 color) {
        this.color = color;
    }
}
