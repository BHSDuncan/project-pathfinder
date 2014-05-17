package com.bhs.pathfinder.structures;

import java.util.Random;

/**
 * Created by Duncan on 16/05/2014.
 */
public class RandomCompleteUndirectedGraph extends Graph {
    private int numVertices = 0;
    private int numEdges = 0;

    private float radius = 0.1f;

    private int maxEdgeWeight = 0;

    public RandomCompleteUndirectedGraph() {
        Random random = new Random();
        this.maxEdgeWeight = Math.abs(random.nextInt());
    }

    public RandomCompleteUndirectedGraph(int maxEdgeWeight) {
        this.maxEdgeWeight = maxEdgeWeight;
    }

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
            float[] point = this.generateSafePoint();

            Vertex v = new Vertex(point[0], point[1], radius);

            if (i == 0) {
                v.setVertexState(Vertex.VERTEX_STATE.START);
            } else if (i == this.numVertices - 1) {
                v.setVertexState(Vertex.VERTEX_STATE.END);
            }

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
            boolean haveFloat = false;

            while (!haveFloat) {
                x = random.nextFloat() * 2 - 1;
                y = random.nextFloat() * 2 - 1;

                if (Math.abs(x) < 1 - this.radius && Math.abs(y) < 1 - this.radius) {
                    haveFloat = true;
                }
            }

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
        Random random = new Random();

        for (Vertex v : this.vertices) {
            for (int i = 0; i < this.vertices.size(); i++) {
                Vertex v2 = this.vertices.get(i);

                if (v.equals(v2)) {
                    continue;
                }

                Edge e = new Edge(v, v2);
                e.setWeight(random.nextInt(this.maxEdgeWeight));
                this.addEdge(e);
            }
        }
    }
}
