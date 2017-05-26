package fr.givel.basebuilding.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import fr.givel.basebuilding.model.GameItem;

/**
 * Created by lmg on 25/05/17.
 */

public class GameItemView {
    private Bitmap bmp;
    private GameItem item;
    private int xSize, ySize, zSize;

    public GameItemView(Bitmap bmp, int zSize) {
        setBmp(bmp, zSize);
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

    public GameItem getItem() {
        return item;
    }

    public void setItem(GameItem item) {
        this.item = item;
    }

    public void onDraw(Canvas canvas, int worldLayer, Paint paint) {
        int layerToDraw = worldLayer - this.item.getCoordinate().z;
        int srcX = layerToDraw * xSize;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + xSize, srcY + ySize);
        int x = this.item.getCoordinate().x;
        int y = this.item.getCoordinate().y - worldLayer;
        Rect dst = new Rect(x, y, x + xSize, y + ySize);
        canvas.drawBitmap(bmp, src, dst, paint);
    }
}
