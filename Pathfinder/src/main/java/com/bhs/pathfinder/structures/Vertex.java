package com.bhs.pathfinder.structures;

import android.graphics.Color;

import com.bhs.pathfinder.util.Colours;
import com.bhs.pathfinder.views.shapes.Circle;

/**
 * Created by Duncan on 16/05/2014.
 */
public class Vertex {
    public enum VERTEX_STATE {
        START,
        END,
        UNSELECTED,
        SELECTED
    }

    private Circle graphic;
    private VERTEX_STATE vertexState;

    public Vertex(float x, float y, float r) {
        graphic = new Circle(x, y, r);
        this.vertexState = VERTEX_STATE.UNSELECTED;
        this.setColourBasedOnState();
    }

    public float getRadius() {
        return this.graphic.getRadius();
    }

    public float getX() {
        return this.graphic.getX();
    }

    public float getY() {
        return this.graphic.getY();
    }

    public Circle getGraphic() {
        return this.graphic;
    }

    public void setVertexState(VERTEX_STATE vertexState) {
        this.vertexState = vertexState;
        this.setColourBasedOnState();
    }

    public VERTEX_STATE getVertexState() {
        return this.vertexState;
    }

    private void setColourBasedOnState() {
        switch (this.vertexState) {
            case START:
            case SELECTED:
                this.graphic.setColour(Colours.WHITE);
                break;
            case END:
                this.graphic.setColour(Colours.RED);
                break;
            case UNSELECTED:
            default:
                this.graphic.setColour(Colours.GREEN);
        }
    }

    public void tap() {
        switch (this.vertexState) {
            case UNSELECTED:
                this.setVertexState(VERTEX_STATE.SELECTED);
                break;
            case SELECTED:
                this.setVertexState(VERTEX_STATE.UNSELECTED);
                break;
            default:
        }

        this.setColourBasedOnState();
    }
}
