package fr.givel.basebuilding.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import fr.givel.basebuilding.utils.Coordinate;

/**
 * Created by lmg on 25/05/17.
 */

public class GameItemView {
    private Bitmap bmp;
    private List<Bitmap> bitmapList;
    private int xSize = 21, ySize = 11, zSize;
    private Paint paint;
    private int pixelDensity = 4;
    private int distanceBetweenLayers = 2;

    public GameItemView(Bitmap bmp) {
        bitmapList = new ArrayList<>();
        setBmp(bmp);
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
        zSize = bmp.getWidth() / xSize;

        paint = new Paint();
        paint.setDither(false);
        paint.setAntiAlias(false);

        for (int i = 0; i < zSize; i++) {
            // Create Bitmap of the correct size
            int diag = (int) Math.sqrt(xSize * xSize + ySize * ySize);
            Bitmap bmpLayer = Bitmap.createBitmap(diag * pixelDensity, diag * pixelDensity, Bitmap.Config.ARGB_8888);

            //copy the bitmap in the middle using a canvas
            Canvas bmpCanvas = new Canvas(bmpLayer);
            bmpCanvas.save();
            bmpCanvas.scale(pixelDensity, pixelDensity);

            Rect src = new Rect(i * xSize, 0, (i + 1) * xSize, ySize);
            int xDest = (diag - xSize) / 2, yDest = (diag - ySize) / 2;
            Rect dst = new Rect(xDest, yDest, xDest + xSize, yDest + ySize);
            bmpCanvas.drawBitmap(bmp, src, dst, paint);
            bmpCanvas.restore();
            bitmapList.add(bmpLayer);
        }
    }

    private Bitmap rotatedLayer(Bitmap bmp, float deg) {
        // Create Bitmap of the correct size
        int diag = (int) Math.sqrt(bmp.getWidth() * bmp.getHeight() + bmp.getHeight() * bmp.getHeight());
        Bitmap bmpLayer = Bitmap.createBitmap(diag, diag, Bitmap.Config.ARGB_8888);

        //copy the bitmap in the middle of the rotated canvas
        Canvas bmpCanvas = new Canvas(bmpLayer);
        bmpCanvas.save();
        bmpCanvas.rotate(deg, diag / 2, diag / 2);
        Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        int xDest = (diag - bmp.getWidth()) / 2, yDest = (diag - bmp.getHeight()) / 2;
        Rect dst = new Rect(xDest, yDest, xDest + bmp.getWidth(), yDest + bmp.getHeight());
        bmpCanvas.drawBitmap(bmp, src, dst, paint);
        bmpCanvas.restore();
        return bmpLayer;
    }

    public void onDraw(Canvas canvas, int worldLayer, Paint paint, Coordinate item) {
        // The layer of the object we need to draw
        int layerToDraw = worldLayer - item.z;

        // If it exists, draw it
        if (layerToDraw >= 0 && layerToDraw < zSize) {
            Bitmap layerBitmap = rotatedLayer(bitmapList.get(layerToDraw), item.rotation);

            Rect src = new Rect(0, 0, layerBitmap.getWidth(), layerBitmap.getHeight());

            int x = item.x;
            int y = item.y - worldLayer * distanceBetweenLayers;

            Rect dst = new Rect(x, y, x + layerBitmap.getWidth(), y + layerBitmap.getHeight());

            canvas.drawBitmap(layerBitmap, src, dst, paint);
        }
    }
}
