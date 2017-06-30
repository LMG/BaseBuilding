package fr.givel.basebuilding.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import fr.givel.basebuilding.model.GameItem;
import fr.givel.basebuilding.model.World;

/**
 * Created by lmg on 25/05/17.
 * Displays a world from a given camera
 */

public class View3D extends SurfaceView {
    private static final int MAX_LAYER = 256;
    private static final String TAG = "view";
    Camera currentCamera;

    private SurfaceHolder holder;
    private World world;
    private Paint paint;
    private DisplayMetrics metrics;

    public View3D(Context context) {
        super(context);
    }

    public View3D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public View3D(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View3D(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    public void initView(Context c) {
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setWillNotDraw(false);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        paint = new Paint();
        paint.setDither(false);
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);

        currentCamera = world.getCamera(0);

        metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "canvas.ishardwareaccelerated: " + canvas.isHardwareAccelerated());
        super.onDraw(canvas);
        Log.d(TAG, "begin " + System.currentTimeMillis());

        canvas.drawColor(Color.BLUE);

        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        //We'll draw everything on a small screen then blow it up
        Bitmap smallScreen = Bitmap.createBitmap(
                (int) (metrics.widthPixels / currentCamera.getZoom()),
                (int) (metrics.heightPixels / currentCamera.getZoom()),
                Bitmap.Config.ARGB_8888);
        Canvas smallCanvas = new Canvas(smallScreen);

        for (int i = 0; i < MAX_LAYER; i++) {
            for (GameItem item : world.getGameItems()) {
                item.getView().onDraw(smallCanvas, i, paint, item.getCoordinate());
            }
        }
        Matrix scale = new Matrix();
        scale.setScale(currentCamera.getZoom(), currentCamera.getZoom());
        canvas.drawBitmap(smallScreen, scale, paint);

        Log.d(TAG, "end " + System.currentTimeMillis());
    }
}
