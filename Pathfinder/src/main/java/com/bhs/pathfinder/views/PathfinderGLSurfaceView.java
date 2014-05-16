package com.bhs.pathfinder.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.bhs.pathfinder.interfaces.UserInputListener;
import com.bhs.pathfinder.structures.Edge;
import com.bhs.pathfinder.structures.Graph;
import com.bhs.pathfinder.structures.Vertex;

/**
 * Created by duncan on 5/15/2014.
 */
public class PathfinderGLSurfaceView extends GLSurfaceView {

    private float curX = 0f;
    private float curY = 0f;

    private UserInputListener userInputListener;

    private PathfinderGLSurfaceViewRenderer renderer;

    private Graph graph;

    public PathfinderGLSurfaceView(Context context){
        super(context);

        this.userInputListener = (UserInputListener)context;

        setEGLContextClientVersion(2);

        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);

        this.renderer = new PathfinderGLSurfaceViewRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(this.renderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void init(Graph graph) {
        this.graph = graph;

        this.sendGraphToRenderer();
    }

    private void sendGraphToRenderer() {
        for (Vertex v : this.graph.getVertices()) {
            this.renderer.addShape(v.getGraphic());
        }

        for (Edge e : this.graph.getEdges()) {
            this.renderer.addShape(e.getGraphic());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.curX = e.getX();
                this.curY = e.getY();
                break;
            case MotionEvent.ACTION_UP:
                this.userInputListener.handleTap(this.curX, this.curY);
        }

        return true;
    }
}
