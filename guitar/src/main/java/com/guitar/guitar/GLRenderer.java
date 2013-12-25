package com.guitar.guitar;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.guitar.guitar.glprimitive.GLDCircle;
import com.guitar.guitar.glprimitive.GLDLine;
import com.guitar.guitar.glprimitive.GLShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Saiber on 14.12.13.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    int width, height;
    float ordinate = 1;
    float abscissa = 1;
    float ratio = 0;
    private int fretCount = 0;

    List<GLShape>guitarStrings = Collections.synchronizedList(new ArrayList<GLShape>());
    List<GLShape>guitarFretboard = Collections.synchronizedList(new ArrayList<GLShape>());

    public GLRenderer(int fretCount) {
        this.fretCount = fretCount;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        ratio = (float)width / (float)height;
        Log.i("info"," Ratio = " + Float.toString(ratio));
        float h = height / 7;
        ordinate = 7;         //Y
        abscissa = width / h; //X


        if(height == 0)
            height = 1;
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_ALPHA_TEST);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glViewport(0, 0, width, height);
        gl.glLoadIdentity();
        gl.glOrthof(0.0f, abscissa, 0.0f, ordinate, -1.0f, 1.0f);

        guitarFretboard.clear();
        float distance = 1.8f;//abscissa / (fretCount );
        float currentDistance = abscissa - distance;
        float fretH = 0;
        for(int i = 0; i < fretCount-1; i++) {
            GLDLine fret = new GLDLine(currentDistance,ordinate/2, 0.2f, ordinate - 1.5f + fretH);
            fret.setColor(0.1f, 0.1f, 0.1f, 0.001f);
            guitarFretboard.add(fret);
            currentDistance -= distance;
            fretH += 0.06f;
        }

        guitarStrings.clear();
        float lineAlpha = 0.2f;
        float lineHeight = 0.05f;
        for(int i = 1; i <= 6; i++) {
            GLDLine line = new GLDLine(abscissa/2,i,abscissa,lineHeight);
            line.setColor(0.6f, 0.6f, 0.6f, lineAlpha);
            guitarStrings.add(line);
            lineAlpha+= 0.05f;
            lineHeight+= 0.002f;
        }
        GLDCircle circle = new GLDCircle(-2.0f, ordinate/2, 0, 4.5f);
        circle.setColor(0.675f, 0.675f, 0.675f, 0.25f);
        guitarFretboard.add(circle);

        circle = new GLDCircle(-2.0f, ordinate/2, 0, 4.25f);
        circle.setColor(0.65f, 0.65f, 0.65f, 0.3f);
        guitarFretboard.add(circle);

        circle = new GLDCircle(-2.0f, ordinate/2, 0, 4.0f);
        circle.setColor(0.625f, 0.625f, 0.625f, 0.35f);
        guitarFretboard.add(circle);

        /*((GLDLine)guitarStrings.get(0)).setRotateAngle(0.5f);
        ((GLDLine)guitarStrings.get(1)).setRotateAngle(0.5f);
        ((GLDLine)guitarStrings.get(4)).setRotateAngle(-0.5f);
        ((GLDLine)guitarStrings.get(5)).setRotateAngle(-0.5f);*/

    }

    public int getFrentCount() {
        return fretCount;
    }
    public float getAbscissa() {
        return abscissa;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        synchronized (guitarFretboard) {
            for(GLShape object : guitarFretboard) {
                object.draw(gl);
            }
        }
        synchronized (guitarStrings) {
            for(GLShape object : guitarStrings) {
                object.draw(gl);
            }
        }
    }
}
