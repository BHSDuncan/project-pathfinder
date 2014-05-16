package com.bhs.pathfinder.activities;

import com.bhs.pathfinder.interfaces.UserInputListener;
import com.bhs.pathfinder.structures.RandomCompleteUndirectedGraph;
import com.bhs.pathfinder.util.SystemUiHider;
import com.bhs.pathfinder.views.PathfinderGLSurfaceView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;


public class MainGameActivity extends Activity implements UserInputListener {
    private PathfinderGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new PathfinderGLSurfaceView(this);

        RandomCompleteUndirectedGraph graph = new RandomCompleteUndirectedGraph();
        graph.init(5);

        mGLView.init(graph);

        setContentView(mGLView);
    }

    @Override
    public void handleTap(float x, float y) {

    }
}
