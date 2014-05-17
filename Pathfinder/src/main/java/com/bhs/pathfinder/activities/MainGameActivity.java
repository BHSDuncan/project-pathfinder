package com.bhs.pathfinder.activities;

import com.bhs.pathfinder.interfaces.UserInputListener;
import com.bhs.pathfinder.structures.Graph;
import com.bhs.pathfinder.structures.HexagonalConnectedUndirectedGraph;
import com.bhs.pathfinder.structures.RandomCompleteUndirectedGraph;
import com.bhs.pathfinder.structures.Vertex;
import com.bhs.pathfinder.views.PathfinderGLSurfaceView;
import com.bhs.pathfinder.views.PathfinderGLSurfaceViewRenderer;
import com.bhs.pathfinder.util.ScreenUtil;

import android.app.Activity;
import android.os.Bundle;


public class MainGameActivity extends Activity implements UserInputListener {
    private PathfinderGLSurfaceView mGLView;
    private Graph graph;

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

            // update state
            if (v != null) {
                v.tap();
            }
        }
    }

}
