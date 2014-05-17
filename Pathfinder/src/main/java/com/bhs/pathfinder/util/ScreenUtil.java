package com.bhs.pathfinder.util;

import android.opengl.Matrix;

/**
 * Created by Duncan on 17/05/2014.
 */
public class ScreenUtil {
    public static float[] getWorldCoords(float x, float y, int width, int height, float[] projM, float[] viewM) {
        int screenW = width;
        int screenH = height;

        float[] worldPos;

        // Auxiliary matrix and vectors
        // to deal with ogl.
        float[] invertedMatrix, transformMatrix,
                normalizedInPoint, outPoint;
        invertedMatrix = new float[16];
        transformMatrix = new float[16];
        normalizedInPoint = new float[4];
        outPoint = new float[4];

        // Invert y coordinate, as android uses
        // top-left, and ogl bottom-left.
        //int oglTouchY = (int) (screenH - y);
        float oglTouchY = (screenH - y);

       /* Transform the screen point to clip
       space in ogl (-1,1) */
        normalizedInPoint[0] =
                (float) (x * 2.0f / screenW - 1.0);
        normalizedInPoint[1] =
                (float) ((oglTouchY) * 2.0f / screenH - 1.0);
        normalizedInPoint[2] = 0f;
        normalizedInPoint[3] = 1.0f;

       /* Obtain the transform matrix and
       then the inverse. */
        Matrix.multiplyMM(
                transformMatrix, 0,
                projM, 0,
                viewM, 0);
        Matrix.invertM(invertedMatrix, 0,
                transformMatrix, 0);

       /* Apply the inverse to the point
       in clip space */
        Matrix.multiplyMV(
                outPoint, 0,
                invertedMatrix, 0,
                normalizedInPoint, 0);

        if (outPoint[3] == 0.0)
        {
            // Avoid /0 error.
            //Log.e("World coords", "ERROR!");
            return null;
        }

        // Divide by the 3rd component to find
        // out the real position.
        worldPos = new float[]{outPoint[0] / outPoint[3], outPoint[1] / outPoint[3] };

        return worldPos;
    }
}
