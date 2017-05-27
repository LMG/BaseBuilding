package fr.givel.basebuilding.utils;

/**
 * Created by lmg on 27/05/17.
 */

public class Vect2D {
    private double x;
    private double y;

    public static Vect2D createPolar(double length, double angle) {
        Vect2D res = new Vect2D();

        res.x = length * Math.cos(angle);
        res.y = length * Math.sin(angle);

        return res;
    }

    public static Vect2D createCart(Coordinate start, Coordinate end) {
        Vect2D res = new Vect2D();

        res.x = end.x - start.x;
        res.y = end.y - start.y;

        return res;
    }

    public static Vect2D createCart(double x, double y) {
        Vect2D res = new Vect2D();

        res.x = x;
        res.y = y;

        return res;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void setLength(double length) {
        double angle = this.getAngle();

        this.x = length * Math.cos(angle);
        this.y = length * Math.sin(angle);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void setAngle(double angle) {
        double length = this.getLength();

        this.x = length * Math.cos(angle);
        this.y = length * Math.sin(angle);

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(Vect2D vect) {
        this.x += vect.x;
        this.y += vect.y;
    }

    public void sub(Vect2D vect) {
        this.x -= vect.x;
        this.y -= vect.y;
    }

    public String toString() {
        return "x: " + x + "y:" + y + "length: " + getLength() + "angle" + getAngle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vect2D vect2D = (Vect2D) o;

        if (Double.compare(vect2D.x, x) != 0) return false;
        return Double.compare(vect2D.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
