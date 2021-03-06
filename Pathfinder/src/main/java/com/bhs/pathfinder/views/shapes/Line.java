package com.bhs.pathfinder.views.shapes;

import android.opengl.GLES20;

import com.bhs.pathfinder.views.PathfinderGLSurfaceViewRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Duncan on 16/05/2014.
 */
public class Line extends Shape {

    private static final int COORDS_PER_VERTEX = 3;

    private final int vertexStride = COORDS_PER_VERTEX * 4;

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float width;

    private FloatBuffer vertexBuffer;

    private int mProgram = -1;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    private int vertexCount;
    private float buffer[];
    float color[] = { 100.0f, 100.0f, 100.0f, 1.0f };

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +

                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public Line(float startX, float startY, float endX, float endY, float width) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.width = width;

        this.vertexCount = 2;

        buffer = new float[6];

        fillBuffers();
    }

    public void init() {
        if (this.mProgram == -1) {
            int vertexShader = PathfinderGLSurfaceViewRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
            int fragmentShader = PathfinderGLSurfaceViewRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

            mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
            GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
            GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
            GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables
        }
    }

    public void draw() {

    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(this.mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    private void fillBuffers() {
        int idx = 0;

        //center vertex for triangle fan
        buffer[idx++] = this.startX;
        buffer[idx++] = this.startY;
        buffer[idx++] = -0.01f;
        buffer[idx++] = this.endX;
        buffer[idx++] = this.endY;
        buffer[idx++] = -0.01f;

        this.vertexCount = this.buffer.length;

        fillFloatBuffer();
    }

    private void fillFloatBuffer() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                this.buffer.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(this.buffer);
        vertexBuffer.position(0);
    }
}
