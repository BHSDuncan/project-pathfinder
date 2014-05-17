package com.bhs.pathfinder.views.shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by duncan on 5/15/2014.
 */
public abstract class Shape {
    private float[] colour;

    public abstract void draw();
    public abstract void draw(float[] mvpMatrix);
    public abstract void init();

    public void setColour(float[] colour) {
        this.colour = colour;
    }

    public float[] getColour() {
        return this.colour;
    }
}
