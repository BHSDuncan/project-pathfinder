
package com.bhs.pathfinder.structures;

import java.util.Random;

/**
 * Created by Duncan on 17/05/2014.
 */
public class HexagonalConnectedUndirectedGraph extends Graph {
    private int numVertices = 0;
    private int numEdges = 0;

    private float radius = 0.1f;

    private int maxEdgeWeight = 0;

        public HexagonalConnectedUndirectedGraph() {
            Random random = new Random();
            this.maxEdgeWeight = Math.abs(random.nextInt());
        }

        public HexagonalConnectedUndirectedGraph(int maxEdgeWeight) {
            this.maxEdgeWeight = maxEdgeWeight;
        }

        public void init() {
            this.numVertices = 6;
            this.numEdges = numVertices * (numVertices - 1);

            this.generateGraph();
        }

    private void generateGraph() {
        this.generateVertices();
        this.generateEdges();
    }

    private void generateVertices() {
        Vertex v1 = new Vertex(-0.5f, 0.75f, radius);
        Vertex v2 = new Vertex(0.5f, 0.75f, radius);
        Vertex v3 = new Vertex(0.75f, 0f, radius);
        Vertex v4 = new Vertex(0.5f, -0.75f, radius);
        Vertex v5 = new Vertex(-0.5f, -0.75f, radius);
        Vertex v6 = new Vertex(-0.75f, 0f, radius);

        v1.setVertexState(Vertex.VERTEX_STATE.START);
        v3.setVertexState(Vertex.VERTEX_STATE.END);

        this.addVertex(v1);
        this.addVertex(v2);
        this.addVertex(v3);
        this.addVertex(v4);
        this.addVertex(v5);
        this.addVertex(v6);
    }

    private void generateEdges() {
        Random random = new Random();

        for (int i = 0; i < this.vertices.size(); i++) {
            Vertex v1 = this.vertices.get(i);
            Vertex v2;

            if (i + 1 >= this.vertices.size()) {
                v2 = this.vertices.get(0);
            } else {
                v2 = this.vertices.get(i + 1);
            } // if

            Edge e = new Edge(v1, v2);
            e.setWeight(random.nextInt(this.maxEdgeWeight));
            this.addEdge(e);
        }
    }
}
