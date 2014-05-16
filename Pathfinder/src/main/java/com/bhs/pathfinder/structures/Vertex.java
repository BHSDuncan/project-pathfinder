package com.bhs.pathfinder.structures;

import com.bhs.pathfinder.views.shapes.Circle;

/**
 * Created by Duncan on 16/05/2014.
 */
public class Vertex {
    private Circle graphic;

    public Vertex(float x, float y, float r) {
        graphic = new Circle(x, y, r);
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
}
