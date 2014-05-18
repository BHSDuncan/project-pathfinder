package com.bhs.pathfinder.structures;

import com.bhs.pathfinder.views.shapes.Line;

/**
 * Created by Duncan on 16/05/2014.
 */
public class Edge {
    private Line graphic;
    private Vertex from;
    private Vertex to;

    private int weight = 0;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;

        graphic = new Line(from.getX(), from.getY(), to.getX(), to.getY(), 0.1f);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public Vertex getFrom() {
        return this.from;
    }

    public Vertex getTo() {
        return this.to;
    }

    public Line getGraphic() {
        return this.graphic;
    }
}
