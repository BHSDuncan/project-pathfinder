package com.bhs.pathfinder.structures;

import java.util.Vector;

/**
 * Created by Duncan on 16/05/2014.
 */
public abstract class Graph {
    protected Vector<Vertex> vertices = new Vector<Vertex>();
    protected Vector<Edge> edges = new Vector<Edge>();

    public void addVertex(Vertex v) {
        this.vertices.add(v);
    }

    public void addVertices(Vector<Vertex> vs) {
        this.vertices = vs;
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
    }

    public void addEdges(Vector<Edge> es) {
        this.edges = es;
    }

    public Vector<Vertex> getVertices() {
        return this.vertices;
    }

    public Vector<Edge> getEdges() {
        return this.edges;
    }

    public Vertex findVertex(float x, float y) {
        Vertex toReturn = null;

        for (Vertex v : this.vertices) {
            float left = v.getX() - v.getRadius();
            float right = v.getX() + v.getRadius();
            float top = v.getY() + v.getRadius();
            float bottom = v.getY() - v.getRadius();

            if (x >= left && x <= right && y >= bottom && y <= top) {
                toReturn = v;
            }
        }

        return toReturn;
    }
}
