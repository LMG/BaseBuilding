package fr.givel.basebuilding.utils;

/**
 * Created by lmg on 25/05/17.
 */

public class Coordinate {
    /**
     * (0,0,0) = top left corner on lowest layer (for GameItemView as well as sprite)
     */
    public double x = 0, y = 0, z = 0;
    public double rotation = 0;

    public Coordinate(double x, double y, double z, double rotation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    public Coordinate() {
        this(0, 0, 0, 0);
    }

    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
        this.z = coord.z;
        this.rotation = coord.rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;
        return Double.compare(that.rotation, rotation) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rotation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double planalDistanceTo(Coordinate c) {
        return Math.sqrt((x - c.x) * (x - c.x) + (y - c.y) * (y - c.y));
    }

    /*
     * Returns if it is around in a square of side radius
     */
    public boolean vincinityOf(Coordinate c, int radius) {
        return ((Math.abs(c.x - x) < radius) && (Math.abs(c.y - y) < radius));
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", rotation=" + rotation +
                '}';
    }
}
