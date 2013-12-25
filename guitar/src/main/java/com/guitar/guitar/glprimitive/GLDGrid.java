package com.guitar.guitar.glprimitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLDGrid extends GLShape {

    private float Red = 1.0f;
    private float Green = 1.0f;
    private float Blue = 1.0f;
    private float Alpha = 1.0f;

    private int GRID_COUNT = 10;
    private FloatBuffer vertexBuffer;
    private float[] vertices;

    GLDGrid() {
        vertices = new float[(GRID_COUNT * 6 + 6) * 2];
        float x1 = 0;
        for (int i = 0; i < (GRID_COUNT * 6 + 6) * 2; i += 12) {
            vertices[i] = x1;
            vertices[i + 1] = 0;
            vertices[i + 2] = 0;
            vertices[i + 3] = x1;
            vertices[i + 4] = -GRID_COUNT;
            vertices[i + 5] = 0;

            vertices[i + 6] = 0;
            vertices[i + 7] = -x1;
            vertices[i + 8] = 0;
            vertices[i + 9] = GRID_COUNT;
            vertices[i + 10] = -x1;
            vertices[i + 11] = 0;
            x1 += 1;
        }
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

    }

    public GLDGrid(int size) {
        int lines = ((size * 6 + 6) * 2);
        vertices = new float[lines];
        float x = 0;

        for (int i = 0; i < lines; i += 12) {
            vertices[i] = x;
            vertices[i + 1] = 0.0f;
            vertices[i + 2] = 0.0f;
            vertices[i + 3] = x;
            vertices[i + 4] = -size;
            vertices[i + 5] = 0.0f;

            vertices[i + 6] = 0;
            vertices[i + 7] = -x;
            vertices[i + 8] = 0;
            vertices[i + 9] = size;
            vertices[i + 10] = -x;
            vertices[i + 11] = 0;
            x += 1;
        }
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColor4f(Red, Green, Blue, 1.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_LINES, 0, vertices.length / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void setColor(float red, float green, float blue, float alpha) {
        this.Red = red;
        this.Green = green;
        this.Blue = blue;
        this.Alpha = alpha;
    }

    @Override
    public void setAlpha(float alpha) {
        this.Alpha = alpha;
        setColor(this.Red, this.Green, this.Blue, this.Alpha);
    }
}
