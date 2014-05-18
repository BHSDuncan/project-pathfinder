package com.bhs.pathfinder.activities;

import com.bhs.pathfinder.interfaces.UserInputListener;
import com.bhs.pathfinder.structures.Edge;
import com.bhs.pathfinder.structures.Graph;
import com.bhs.pathfinder.structures.HexagonalConnectedUndirectedGraph;
import com.bhs.pathfinder.structures.RandomCompleteUndirectedGraph;
import com.bhs.pathfinder.structures.Vertex;
import com.bhs.pathfinder.views.PathfinderGLSurfaceView;
import com.bhs.pathfinder.views.PathfinderGLSurfaceViewRenderer;
import com.bhs.pathfinder.util.ScreenUtil;
import java.util.Stack;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;


public class MainGameActivity extends Activity implements UserInputListener {
    private PathfinderGLSurfaceView mGLView;
    private Graph graph;

    private Stack<Vertex> path = new Stack<Vertex>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new PathfinderGLSurfaceView(this);

        //RandomCompleteUndirectedGraph randGraph = new RandomCompleteUndirectedGraph();
        //randGraph.init(5);
        HexagonalConnectedUndirectedGraph hGraph = new HexagonalConnectedUndirectedGraph(50);
        hGraph.init();

        mGLView.init(hGraph);

        this.graph = hGraph;

        path.push(this.graph.getStartVertex());

        setContentView(mGLView);
    }

    @Override
    public void handleTap(float x, float y) {
        // translate screen coords to world coords
        PathfinderGLSurfaceViewRenderer r = this.mGLView.getRenderer();
        float[] worldCoords = ScreenUtil.getWorldCoords(x, y, r.getScreenW(), r.getScreenH(), r.getProjectionM(), r.getViewM());

        if (worldCoords != null) {
            // find vertex
            Vertex v = this.graph.findVertex(worldCoords[0], worldCoords[1]);

            // update state if:
            // 1. we have a legitimate vertex,
            // 2. the vertex isn't already in the path,
            // 3. the vertex is either connected to the most-recent selected vertex, or, the vertex is the last vertex in the path
            if (v != null && (!path.contains(v) && isConnectedToLastVertex(v)) || this.path.peek().equals(v)) {
                v.tap();

                this.updateStack(v);
            }
        }
    }

    private boolean isConnectedToLastVertex(Vertex v1) {
        Vector<Edge> es = this.graph.getEdges();
        Vertex v2 = this.path.peek();

        boolean toRet = false;

        for (Edge e : es) {
            if ((e.getFrom().equals(v1) && e.getTo().equals(v2)) || (e.getFrom().equals(v2) && e.getTo().equals(v1))) {
                toRet = true;
                break;
            }
        }

        return toRet;
    }

    private void updateStack(Vertex v) {
        switch (v.getVertexState()) {
            case SELECTED:
                this.path.push(v);
                break;
            case UNSELECTED:
                this.path.pop();
                break;
            default:
        }
    }

}
