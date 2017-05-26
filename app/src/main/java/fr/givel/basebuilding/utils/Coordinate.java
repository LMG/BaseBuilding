package fr.givel.basebuilding.utils;

/**
 * Created by lmg on 25/05/17.
 */

public class Coordinate {
    /**
     * (0,0,0) = top left corner on lowest layer (for GameItemView as well as sprite)
     */
    public int x, y, z;
    public float rotation;

    public Coordinate(int x, int y, int z, float rotation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }
}
