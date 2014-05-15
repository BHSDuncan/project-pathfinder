package com.bhs.pathfinder.views;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by duncan on 5/15/2014.
 */
public class PathfinderGLSurfaceView extends GLSurfaceView {

    public PathfinderGLSurfaceView(Context context){
        super(context);

        setEGLContextClientVersion(2);

        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new PathfinderGLSurfaceViewRenderer());

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
