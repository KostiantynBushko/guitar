package com.guitar.guitar.glprimitive;

import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLDCircle extends GLShape {

    private float Red = 1.0f;
    private float Green = 0.0f;
    private float Blue = 0.0f;
    private float Alpha = 0.1f;

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer holeVertexBuffer;
    private FloatBuffer holeColorBuffer;

    private float[] vertices;
    private float[] hole;
    private float[] color;
    private float[] holeColor;
    private int segments = 365;

    private float centerX = 0;
    private float centerY = 0;


    public GLDCircle(float X, float Y, float Z, float Radius) {
        this.row = (int) Math.abs(Y);
        this.column = (int) (X - 0.5f);
        centerX = X;
        centerY = Y;

        int vertexCount = segments * 9;
        int colorCount = segments * 3 * 4;

        float k_increment = 2.0f * 3.14f / segments;
        float theta = 0.0f;
        Radius = Radius - 0.05f;

        vertices = new float[vertexCount];
        for (int i = 0; i < vertexCount - 1; i += 9) {
            vertices[i] = X;
            vertices[i + 1] = Y;
            vertices[i + 2] = Z;

            vertices[i + 3] = X + Radius * (float) Math.cos(theta);
            vertices[i + 4] = Y + Radius * (float) Math.sin(theta);
            vertices[i + 5] = Z;

            theta += k_increment;

            vertices[i + 6] = X + Radius * (float) Math.cos(theta);
            vertices[i + 7] = Y + Radius * (float) Math.sin(theta);
            vertices[i + 8] = Z;
        }

        vertices[vertexCount - 3] = vertices[3];
        vertices[vertexCount - 2] = vertices[4];
        vertices[vertexCount - 1] = vertices[5];

        color = new float[colorCount];
        for (int i = 0; i < colorCount; i += 12) {
            color[i] = Red;
            color[i + 1] = Green;
            color[i + 2] = Blue;
            color[i + 3] = 1.0f;

            color[i + 4] = Red;
            color[i + 5] = Green;
            color[i + 6] = Blue;
            color[i + 7] = Alpha;

            color[i + 8] = Red;
            color[i + 9] = Green;
            color[i + 10] = Blue;
            color[i + 11] = Alpha;
        }

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer colorByteBuffer = ByteBuffer.allocateDirect(color.length * 4);
        colorByteBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = colorByteBuffer.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        // 2
        theta = 0.0f;
        Radius = Radius - 0.01f;
        hole = new float[vertexCount];
        for (int i = 0; i < vertexCount - 1; i += 9) {
            hole[i] = X;
            hole[i + 1] = Y;
            hole[i + 2] = Z;

            hole[i + 3] = X + Radius * (float) Math.cos(theta);
            hole[i + 4] = Y + Radius * (float) Math.sin(theta);
            hole[i + 5] = Z;
            theta += k_increment;

            hole[i + 6] = X + Radius * (float) Math.cos(theta);
            hole[i + 7] = Y + Radius * (float) Math.sin(theta);
            hole[i + 8] = Z;
        }

        hole[vertexCount - 3] = hole[3];
        hole[vertexCount - 2] = hole[4];
        hole[vertexCount - 1] = hole[5];

        holeColor = new float[colorCount];
        for (int i = 0; i < colorCount; i += 12) {
            holeColor[i] = 1.0f;
            holeColor[i + 1] = 0.0f;
            holeColor[i + 2] = 0.0f;
            holeColor[i + 3] = 0.1f;

            holeColor[i + 4] = 1.0f;
            holeColor[i + 5] = 0.0f;
            holeColor[i + 6] = 0.0f;
            holeColor[i + 7] = 0.1f;

            holeColor[i + 8] = 1.0f;
            holeColor[i + 9] = 0.0f;
            holeColor[i + 10] = 0.0f;
            holeColor[i + 11] = 0.1f;
        }
        ByteBuffer holeByteBuffer = ByteBuffer.allocateDirect(hole.length * 4);
        holeByteBuffer.order(ByteOrder.nativeOrder());
        holeVertexBuffer = holeByteBuffer.asFloatBuffer();
        holeVertexBuffer.put(hole);
        holeVertexBuffer.position(0);

        ByteBuffer colorHoleByteBuffer = ByteBuffer.allocateDirect(holeColor.length * 4);
        colorHoleByteBuffer.order(ByteOrder.nativeOrder());
        holeColorBuffer = colorHoleByteBuffer.asFloatBuffer();
        holeColorBuffer.put(holeColor);
        holeColorBuffer.position(0);

        scale = 0.0f;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(100);
                while (scale < 1.5f) {
                    SystemClock.sleep(10);
                    scale += 0.1f;
                }
                while (scale > 1.0f) {
                    SystemClock.sleep(10);
                    scale -= 0.1f;
                }
                scale = 1.0f;
            }
        }).start();

    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();

        gl.glTranslatef(centerX, centerY, 0.0f);
        gl.glScalef(scale, scale, 1.0f);
        gl.glTranslatef(-centerX, -centerY, 0.0f);

        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices.length / 3);

        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        Red = r;
        Green = g;
        Blue = b;
        Alpha = a;
        int colorCount = segments * 3 * 4;
        for (int i = 0; i < colorCount; i += 12) {
            color[i] = Red;
            color[i + 1] = Green;
            color[i + 2] = Blue;
            color[i + 3] = 1.0f;

            color[i + 4] = Red;
            color[i + 5] = Green;
            color[i + 6] = Blue;
            color[i + 7] = Alpha;

            color[i + 8] = Red;
            color[i + 9] = Green;
            color[i + 10] = Blue;
            color[i + 11] = Alpha;
        }
        colorBuffer.put(color);
        colorBuffer.position(0);
    }

    @Override
    public void setAlpha(float alpha) {
        this.Alpha = alpha + 0.1f;
        setColor(this.Red, this.Green, this.Blue, this.Alpha);
    }
}
