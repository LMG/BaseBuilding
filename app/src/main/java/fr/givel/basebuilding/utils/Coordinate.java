package fr.givel.basebuilding.utils;

/**
 * Created by lmg on 25/05/17.
 */

public class Coordinate {
    /**
     * (0,0,0) = top left corner on lowest layer (for GameItemView as well as sprite)
     */
    public int x = 0, y = 0, z = 0;
    public int rotation = 0;

    public Coordinate(int x, int y, int z, int rotation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
