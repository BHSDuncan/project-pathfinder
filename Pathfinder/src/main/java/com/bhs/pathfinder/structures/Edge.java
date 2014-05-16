package com.bhs.pathfinder.structures;

import com.bhs.pathfinder.views.shapes.Line;

/**
 * Created by Duncan on 16/05/2014.
 */
public class Edge {
    private Line graphic;
    private Vertex from;
    private Vertex to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;

        graphic = new Line(from.getX(), from.getY(), to.getX(), to.getY(), 0.1f);
    }

    public Line getGraphic() {
        return this.graphic;
    }
}
