package com.bhs.pathfinder.structures;

import java.util.Random;

/**
 * Created by Duncan on 16/05/2014.
 */
public class RandomCompleteUndirectedGraph extends Graph {
    private int numVertices = 0;
    private int numEdges = 0;

    private float radius = 0.1f;

    public void init(int numVertices) throws IllegalArgumentException {
        if (numVertices < 2) {
            throw new IllegalArgumentException("Need at least 2 vertices.");
        }

        this.numVertices = numVertices;
        this.numEdges = numVertices * (numVertices - 1);

        this.generateGraph();
    }

    private void generateGraph() {
        this.generateVertices();
        this.generateEdges();
    }

    private void generateVertices() {
        for (int i = 0; i < this.numVertices; i++) {
            // TODO: randomize the locations and check we don't have any overlaps
            float[] point = this.generateSafePoint();

            Vertex v = new Vertex(point[0], point[1], radius);
            this.addVertex(v);
        }
    }

    private float[] generateSafePoint() {
        Random random = new Random();
        boolean done = false;

        float point[] = new float[2];
        float x = 0;
        float y = 0;

        while (!done) {
            x = random.nextFloat() * 2 - 1;
            y = random.nextFloat() * 2 - 1;

            int numSafeX = 0;
            int numSafeY = 0;

            for (Vertex v : this.vertices) {
                if (Math.abs(v.getX() - x) > this.radius) {
                    numSafeX++;
                } else {
                    break;
                }

                if (Math.abs(v.getY() - y) > this.radius) {
                    numSafeY++;
                } else {
                    break;
                }
            }

            if (numSafeX == this.vertices.size() && numSafeY == this.vertices.size()) {
                done = true;
            }
        }

        point[0] = x;
        point[1] = y;

        return point;
    }

    private void generateEdges() {
        for (Vertex v : this.vertices) {
            for (int i = 0; i < this.vertices.size(); i++) {
                Vertex v2 = this.vertices.get(i);

                if (v.equals(v2)) {
                    continue;
                }

                Edge e = new Edge(v, v2);
                this.addEdge(e);
            }
        }
    }
}
