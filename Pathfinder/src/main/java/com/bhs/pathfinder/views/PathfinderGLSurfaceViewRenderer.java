package com.bhs.pathfinder.views;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.Matrix;
import java.util.Vector;

import com.bhs.pathfinder.views.shapes.Circle;
import com.bhs.pathfinder.views.shapes.Line;
import com.bhs.pathfinder.views.shapes.Shape;

/**
 * Created by duncan on 5/15/2014.
 */
public class PathfinderGLSurfaceViewRenderer implements GLSurfaceView.Renderer {
    private Vector<Shape> shapes = new Vector<Shape>();

    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    private int screenW = -1;
    private int screenH = -1;

    public int getScreenW() {
        return this.screenW;
    }

    public int getScreenH() {
        return this.screenH;
    }

    public float[] getProjectionM() {
        return this.mProjectionMatrix;
    }

    public float[] getViewM() {
        return this.mViewMatrix;
    }

    public void addShape(Shape s) {
        this.shapes.add(s);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        this.initShapes();

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glEnable( GLES20.GL_DEPTH_TEST );
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );
        GLES20.glDepthRangef(1f, -1f  );
    }

    private void initShapes() {
        for (Shape s : this.shapes) {
            s.init();
        }
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        for (Shape s : this.shapes) {
            s.draw(mMVPMatrix);
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        this.screenW = width;
        this.screenH = height;

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 2, 7);
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
