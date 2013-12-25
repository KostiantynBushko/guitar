package com.guitar.guitar.glprimitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by kbushko on 7/31/13.
 */

public class GLDDecart extends GLShape {

    private float Red = 1.0f;
    private float Green = 0.0f;
    private float Blue = 0.0f;
    private float Alpha = 1.0f;

    private FloatBuffer vertexBuffer;
    private float vertices_y[] = {
            0.0f, 30.0f, 0.0f,
            0.0f, -30.0f, 0.0f
    };

    public GLDDecart() {
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices_y.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices_y);
        vertexBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glColor4f(Red, Green, Blue, Alpha);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_LINES, 0, vertices_y.length / 3);

        gl.glPopMatrix();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        Red = r;
        Green = g;
        Blue = b;
        Alpha = a;
    }

    @Override
    public void setAlpha(float alpha) {
        this.Alpha = alpha;
        setColor(this.Red, this.Green, this.Blue, this.Alpha);
    }

}
