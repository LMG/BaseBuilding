package fr.givel.basebuilding.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import fr.givel.basebuilding.utils.Coordinate;

/**
 * Created by lmg on 25/05/17.
 */

public class GameItemView {
    private Bitmap bmp;
    private int xSize, ySize, zSize;
    private Paint paint;
    private int distanceBetweenLayers = 1;

    public GameItemView(Bitmap bmp, int zSize) {
        setBmp(bmp, zSize);

        paint = new Paint();
        paint.setDither(false);
        paint.setAntiAlias(false);
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp, int zSize) {
        this.bmp = bmp;
        this.zSize = zSize;
        this.xSize = bmp.getWidth() / zSize;
        this.ySize = bmp.getHeight();
    }

    public void onDraw(Canvas canvas, int worldLayer, Paint paint, Coordinate item) {
        // The layer of the object we need to draw
        int layerToDraw = worldLayer - (int) item.z;

        // If it exists, draw it
        if (layerToDraw >= 0 && layerToDraw < zSize) {

            float x = (float) item.x;
            float y = (float) (item.y - worldLayer * distanceBetweenLayers / 4);

            Matrix transform = new Matrix();
            transform.preRotate((float) Math.toDegrees(item.rotation), xSize / 2, ySize / 2);
            transform.postTranslate(x, y);
            Bitmap bmpToDraw = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bmpCanvas = new Canvas(bmpToDraw);
            Rect src = new Rect(layerToDraw * xSize, 0, (layerToDraw+1) * xSize, ySize);
            Rect tgt = new Rect(0, 0, xSize, ySize);
            bmpCanvas.drawBitmap(bmp, src, tgt, paint);
            canvas.drawBitmap(bmpToDraw, transform, paint);
        }
    }
}
