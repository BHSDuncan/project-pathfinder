package com.bhs.pathfinder.views;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.bhs.pathfinder.views.shapes.Circle;

/**
 * Created by duncan on 5/15/2014.
 */
public class PathfinderGLSurfaceViewRenderer implements GLSurfaceView.Renderer {
    private Circle mCircle1;
    private Circle mCircle2;
    private Circle mCircle3;
    private Circle mCircle4;
    private Circle mCircle5;

    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mCircle1 = new Circle(0.5f, 0f, 0.1f);
        mCircle2 = new Circle(0.5f, 0.5f, 0.1f);
        mCircle3 = new Circle(-0.5f, 0f, 0.1f);
        mCircle4 = new Circle(-0.5f, -0.5f, 0.1f);
        mCircle5 = new Circle(0.1f, -0.25f, 0.1f);

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        mCircle1.draw(mMVPMatrix);
        mCircle2.draw(mMVPMatrix);
        mCircle3.draw(mMVPMatrix);
        mCircle4.draw(mMVPMatrix);
        mCircle5.draw(mMVPMatrix);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
