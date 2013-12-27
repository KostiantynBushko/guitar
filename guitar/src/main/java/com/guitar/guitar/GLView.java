package com.guitar.guitar;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by Saiber on 14.12.13.
 */
public class GLView extends GLSurfaceView {
    int width,height;
    public int titleBarH = 0;
    GLRenderer glRenderer;
    Context context;
    SoundPool soundPool;
    Stack stackId = new Stack();
    AssetManager assetManager;
    char[][] mas = new char[6][6];
    long[][] elapsedTime = new long[6][6];
    char[] fretMas = new char[5];

    int pID = -1;
    int _x_,_y_;
    int touchX, touchY;
    Queue<Integer>list = new LinkedList<Integer>();

    boolean isTouch = false;

    public GLView(Context context) {
        super(context);
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove();

        glRenderer = new GLRenderer(6);
        this.setRenderer(glRenderer);
        this.context = context;
        assetManager = context.getAssets();
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++){
                String file = "f_" + Integer.toString(i) + "_" + Integer.toString(j);
                int id = context.getResources().getIdentifier(file,"raw",context.getPackageName());
                soundPool.load(context,id,1);
            }
        }
    }

    public boolean onTouchEvent(final MotionEvent event) {
        this.width = glRenderer.width;
        this.height = glRenderer.height;

        queueEvent(new Runnable() {
            @Override
            public void run() {
                int actionMask = event.getActionMasked();
                int pointerCount = event.getPointerCount();
                int pointIndex = event.getActionIndex();

                int x = (int)((width - event.getX(pointIndex)) / ((width / glRenderer.getAbscissa()) * 1.8f));
                int y = (int)((height - (event.getY(pointIndex) - titleBarH)) / (height / 6));
                int playId = (y + 1) + (6 * x);

                switch(actionMask) {
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        elapsedTime[x][y] = SystemClock.elapsedRealtime();
                        //pID = soundPool.play(playId, 1, 1, 1, 0, 1.0f);
                        Log.i("info", " X = " + Integer.toString(x) + "  Y = " + Integer.toString(y));
                        break; }
                    case MotionEvent.ACTION_DOWN:        {
                        elapsedTime[x][y] = SystemClock.elapsedRealtime();
                        Log.i("info", " X = " + Integer.toString(x) + "  Y = " + Integer.toString(y));
                        //Log.i("info"," play id = " + Integer.toString(playId));
                        //pID = soundPool.play(playId, 1, 1, 1, 0, 1.0f);
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP:  { /*pID = soundPool.play(playId, 1, 1, 1, 0, 1.0f);*/
                        long timeElapsed = SystemClock.elapsedRealtime() - elapsedTime[x][y];
                        if ((SystemClock.elapsedRealtime() - elapsedTime[x][y]) < 100){
                            Log.i("info"," Play sound id = " + Integer.toString(playId));
                            pID = soundPool.play(playId, 1, 1, 1, 0, 1.0f);
                        }
                        //Log.i("info"," - Touch time elapsed = " + Long.toString(timeElapsed));
                        break; }
                    case MotionEvent.ACTION_UP:          { /*pID = soundPool.play(playId, 1, 1, 1, 0, 1.0f);*/
                        long timeElapsed = SystemClock.elapsedRealtime() - elapsedTime[x][y];
                        if ((SystemClock.elapsedRealtime() - elapsedTime[x][y]) < 100){
                            Log.i("info"," Play sound id = " + Integer.toString(playId));
                            pID = soundPool.play(playId, 1, 1, 1, 0, 1.0f);
                        }
                        //Log.i("info"," - Touch time elapsed = " + Long.toString(timeElapsed));
                        break; }
                    default:break;
                }
            }
        });
        return true;
    }

    public int getTitleBarHeight() {
        return ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    @Override
    public void onSizeChanged(int width, int height, int oldw, int oldh) {
        titleBarH = getTitleBarHeight();
    }
}
