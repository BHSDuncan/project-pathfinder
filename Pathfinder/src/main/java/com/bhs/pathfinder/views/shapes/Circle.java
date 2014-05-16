package com.bhs.pathfinder.views.shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;

import com.bhs.pathfinder.views.PathfinderGLSurfaceViewRenderer;

/**
 * Created by duncan on 5/15/2014.
 */
public class Circle extends Shape {
    private int vertexCount = 180;
    private float radius = 0.5f;
    private float centreX = 0.0f;
    private float centreY = 0.0f;

    private float buffer[] = new float[vertexCount * 3]; // (x,y) for each vertex

    private FloatBuffer vertexBuffer;

    private int mProgram = -1;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // Shader code
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "uniform mat4 uMVPMatrix;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";


    public Circle() {
        fillBuffers();

        int vertexShader = PathfinderGLSurfaceViewRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = PathfinderGLSurfaceViewRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables
    }

    public Circle(float x, float y, float radius) {
        this.centreX = x;
        this.centreY = y;
        this.radius = radius;

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

    public float getX() {
        return this.centreX;
    }
    
    public float getY() {
        return this.centreY;
    }
    
    public void draw() {
        // unimplemented for now
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                12, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        float[] color = {0f, 100f, 0f, 1f};
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the circle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    private void fillBuffers() {
        int idx = 0;

        //center vertex for triangle fan
        buffer[idx++] = centreX;
        buffer[idx++] = centreY;
        buffer[idx++] = 0;

        //outer vertices of the circle
        int outerVertexCount = vertexCount - 1;

        for (int i = 0; i < outerVertexCount; ++i) {
            float percent = (i / (float) (outerVertexCount - 1));
            float rad = percent * 2 * (float) Math.PI;

            //vertex position
            float outerX = centreX + radius * (float)Math.cos(rad);
            float outerY = centreY + radius * (float)Math.sin(rad);

            buffer[idx++] = outerX;
            buffer[idx++] = outerY;
            buffer[idx++] = 0;
        }

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
