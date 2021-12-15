package day15;

import java.util.Objects;

public class Coord{
    public int x;
    public int y;
    public int risk;
    public Coord(int x, int y, int risk){
        this.x = x;
        this.y = y;
        this.risk = risk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                ", risk=" + risk +
                '}';
    }
}