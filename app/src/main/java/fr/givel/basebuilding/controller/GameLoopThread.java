package fr.givel.basebuilding.controller;

import android.graphics.Canvas;

import fr.givel.basebuilding.model.MovingGameItem;
import fr.givel.basebuilding.view.View3D;

/**
 * Created by lmg on 25/05/17.
 */

public class GameLoopThread extends Thread {
    private static final String TAG = "GameLoop";
    // desired fps
    private final static int MAX_FPS = 60;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 50;
    // the frame period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    private boolean running;
    private View3D view;

    public GameLoopThread(View3D view) {
        this.view = view;
    }

    @Override
    public void run() {
        Canvas canvas;

        long beginTime;        // the time when the cycle begun
        long timeDiff;        // the time it took for the cycle to execute
        int sleepTime;        // ms to sleep (<0 if we're behind)
        int framesSkipped;    // number of frames being skipped
        running = true;
        sleepTime = 0;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
            try {
                canvas = this.view.getHolder().lockCanvas(null);
                synchronized (this.view.getHolder()) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;    // resetting the frames skipped
                    // update game state
                    this.updateModel();
                    // update the view
                    this.view.postInvalidate();
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        this.updateModel();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    this.view.getHolder().unlockCanvasAndPost(canvas);
                }
            }    // end finally
        }
    }

    private void updateModel() {
        for (MovingGameItem item : view.getWorld().getMovingItems()) {
            item.updatePosition();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
