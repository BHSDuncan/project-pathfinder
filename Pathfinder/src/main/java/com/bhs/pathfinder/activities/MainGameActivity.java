package com.bhs.pathfinder.activities;

import com.bhs.pathfinder.util.SystemUiHider;
import com.bhs.pathfinder.views.PathfinderGLSurfaceView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;


public class MainGameActivity extends Activity {
    private PathfinderGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new PathfinderGLSurfaceView(this);
        setContentView(mGLView);

    }
}
