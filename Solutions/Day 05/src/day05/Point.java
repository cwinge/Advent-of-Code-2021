package day05;

import java.util.Objects;

public class Point {
    private int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true;}
        if(!(o instanceof Point)){ return false;}
        Point p = (Point) o;
        return p.getX() == x && p.getY() == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
