package fr.givel.basebuilding.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import fr.givel.basebuilding.controller.GameLoopThread;
import fr.givel.basebuilding.model.GameItem;
import fr.givel.basebuilding.model.World;

/**
 * Created by lmg on 25/05/17.
 * Displays a world from a given camera
 */

public class View3D extends SurfaceView {
    private static final int MAX_LAYER = 256;
    Camera currentCamera;

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private World world;

    public View3D(Context context) {
        super(context);
        initView();
    }

    public View3D(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public View3D(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public View3D(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public void setCurrentCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private void initView() {
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        for (int i = 0; i < MAX_LAYER; i++) {
            for (GameItem item : world.getGameItems()) {
                item.getView().onDraw(canvas, i);
            }
        }
    }
}
