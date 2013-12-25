package com.guitar.guitar.glprimitive;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLDrawX extends GLShape {

    private float Red = 1.0f;
    private float Green = 1.0f;
    private float Blue = 1.0f;
    private float Alpha = 1.0f;

    private FloatBuffer vertexBuffer;
    private float[] vertices;
    private GLDLine line1;
    private GLDLine line2;

    public GLDrawX(int column, int row) {
        line1 = new GLDLine(column + 0.5f, row + 0.5f, 1.0f, 0.1f);
        line1.setRotateAngle(45);
        line2 = new GLDLine(column + 0.5f, row + 0.5f, 1.0f, 0.1f);
        line2.setRotateAngle(135);

        this.row = row;
        this.column = column;
    }

    @Override
    public void draw(GL10 gl) {
        line1.draw(gl);
        line2.draw(gl);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        Red = r;
        Green = g;
        Blue = b;
        Alpha = a;
        line1.setColor(r, g, b, a);
        line2.setColor(r, g, b, a);
    }

    @Override
    public void setAlpha(float alpha) {
        this.Alpha = alpha * 2;
        setColor(this.Red, this.Green, this.Blue, this.Alpha);
    }
}
