package fr.givel.basebuilding.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import fr.givel.basebuilding.utils.Coordinate;

/**
 * Created by lmg on 25/05/17.
 */

public class GameItemView {
    private Bitmap bmp;
    private List<List<Bitmap>> bitmapList;
    private int xSize = 21, ySize = 11, zSize;
    private Paint paint;
    private int distanceBetweenLayers = 1;

    public GameItemView(Bitmap bmp, int zSize) {
        bitmapList = new ArrayList<>();
        for (int i = 0; i < 360; i++) {
            bitmapList.add(new ArrayList<Bitmap>());
        }
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
        int layerToDraw = worldLayer - item.z;

        // If it exists, draw it
        if (layerToDraw >= 0 && layerToDraw < zSize) {

            int x = item.x;
            float y = item.y - worldLayer * distanceBetweenLayers / 4;

            Matrix transform = new Matrix();
            transform.preRotate(item.rotation, xSize / 2, ySize / 2);
            transform.postTranslate(x, y);

            canvas.drawBitmap(Bitmap.createBitmap(bmp, layerToDraw * xSize, 0, xSize, ySize), transform, paint);
        }
    }
}
