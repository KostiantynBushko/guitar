package com.guitar.guitar.glprimitive;

import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLDLine extends GLShape {
    private float Red = 1.0f;
    private float Green = 0.0f;
    private float Blue = 0.0f;
    private float Alpha = 0.01f;

    private float CenterX = 0;
    private float CenterY = 0;

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private float[] vertices;
    float[] color;

    float width,height;

    private double angle = 0;

    public GLDLine(float centerX, float centerY, float width, float height) {
        CenterX = centerX;
        CenterY = centerY;
        create(width, height);
    }

    private void create(float width, float height) {
        float halfHeight = height / 2;
        float halfWidth = width / 2;
        this.width = width;
        this.height = height;

        vertices = new float[36];

        vertices[0] = CenterX + width / 2;
        vertices[1] = CenterY + halfHeight;
        vertices[2] = 0.0f;
        vertices[3] = CenterX + width / 2;
        vertices[4] = CenterY - halfHeight;
        vertices[5] = 0.0f;
        vertices[6] = CenterX;
        vertices[7] = CenterY - halfHeight;
        vertices[8] = 0.0f;

        vertices[9] = CenterX + width / 2;
        vertices[10] = CenterY + halfHeight;
        vertices[11] = 0.0f;
        vertices[12] = CenterX;
        vertices[13] = CenterY - halfHeight;
        vertices[14] = 0.0f;
        vertices[15] = CenterX;
        vertices[16] = CenterY + halfHeight;
        vertices[17] = 0.0f;

        vertices[18] = CenterX;
        vertices[19] = CenterY - halfHeight;
        vertices[20] = 0.0f;
        vertices[21] = CenterX - halfWidth;
        vertices[22] = CenterY - halfHeight;
        vertices[23] = 0.0f;
        vertices[24] = CenterX;
        vertices[25] = CenterY + halfHeight;
        vertices[26] = 0.0f;

        vertices[27] = CenterX;
        vertices[28] = CenterY + halfHeight;
        vertices[29] = 0.0f;
        vertices[30] = CenterX - halfWidth;
        vertices[31] = CenterY - halfHeight;
        vertices[32] = 0.0f;
        vertices[33] = CenterX - halfWidth;
        vertices[34] = CenterY + halfHeight;
        vertices[35] = 0.0f;

        color = new float[48];

        color[0] = Red;
        color[1] = Green;
        color[2] = Blue;
        color[3] = Alpha;

        color[4] = Red;
        color[5] = Green;
        color[6] = Blue;
        color[7] = Alpha;

        color[8] = Red;
        color[9] = Green;
        color[10] = Blue;
        color[11] = 1.0f;

        color[12] = Red;
        color[13] = Green;
        color[14] = Blue;
        color[15] = Alpha;

        color[16] = Red;
        color[17] = Green;
        color[18] = Blue;
        color[19] = 1.0f;

        color[20] = Red;
        color[21] = Green;
        color[22] = Blue;
        color[23] = 1.0f;

        color[24] = Red;
        color[25] = Green;
        color[26] = Blue;
        color[27] = 1.0f;

        color[28] = Red;
        color[29] = Green;
        color[30] = Blue;
        color[31] = Alpha;

        color[32] = Red;
        color[33] = Green;
        color[34] = Blue;
        color[35] = 1.0f;

        color[36] = Red;
        color[37] = Green;
        color[38] = Blue;
        color[39] = 1.0f;

        color[40] = Red;
        color[41] = Green;
        color[42] = Blue;
        color[43] = Alpha;

        color[44] = Red;
        color[45] = Green;
        color[46] = Blue;
        color[47] = Alpha;

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

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();

        gl.glTranslatef(CenterX, CenterY, 0);
        gl.glRotatef((float) angle, 0, 0, 1);
        gl.glScalef(scale, scale, 1.0f);
        gl.glTranslatef(-CenterX, -CenterY, 0);

        gl.glColor4f(Red, Green, Blue, Alpha);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 12);

        gl.glPopMatrix();
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        Red = r;
        Green = g;
        Blue = b;
        Alpha = a;

        color[0] = Red;     //1
        color[1] = Green;
        color[2] = Blue;
        color[3] = Alpha;

        color[4] = Red;
        color[5] = Green;
        color[6] = Blue;
        color[7] = Alpha;

        color[8] = Red;
        color[9] = Green;
        color[10] = Blue;
        color[11] = 1.0f;

        color[12] = Red;     //2
        color[13] = Green;
        color[14] = Blue;
        color[15] = Alpha;

        color[16] = Red;
        color[17] = Green;
        color[18] = Blue;
        color[19] = 1.0f;

        color[20] = Red;
        color[21] = Green;
        color[22] = Blue;
        color[23] = 1.0f;

        color[24] = Red;
        color[25] = Green;
        color[26] = Blue;
        color[27] = 1.0f;

        color[28] = Red;
        color[29] = Green;
        color[30] = Blue;
        color[31] = Alpha;

        color[32] = Red;
        color[33] = Green;
        color[34] = Blue;
        color[35] = 1.0f;

        color[36] = Red;
        color[37] = Green;
        color[38] = Blue;
        color[39] = 1.0f;

        color[40] = Red;
        color[41] = Green;
        color[42] = Blue;
        color[43] = Alpha;

        color[44] = Red;
        color[45] = Green;
        color[46] = Blue;
        color[47] = Alpha;

        colorBuffer.put(color);
        colorBuffer.position(0);
    }

    public void setRotateAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void setAlpha(float alpha) {
        this.Alpha = alpha;
        setColor(this.Red, this.Green, this.Blue, this.Alpha);
    }

    public void setPosition(){
        float halfHeight = this.height / 2;
        float halfWidth = this.width / 2;
    }
}
