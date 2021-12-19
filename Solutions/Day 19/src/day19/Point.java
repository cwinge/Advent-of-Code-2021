package day19;

import java.util.Objects;

public class Point {
    public int x;
    public int y;
    public int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }

    public Point(Point p1, Point p2) {
        this(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }

    public Point flip(int i) {
        return switch (i) {
            case 0 -> this;
            case 1 -> new Point(x, -y, -z);
            case 2 -> new Point(x, -z, y);
            case 3 -> new Point(-y, -z, x);
            case 4 -> new Point(-x, -z, -y);
            case 5 -> new Point(y, -z, -x);
            default -> null;
        };
    }

    public Point rotate(int i) {
        return switch (i) {
            case 0 -> this;
            case 1 -> new Point(-y, x, z);
            case 2 -> new Point(-x, -y, z);
            case 3 -> new Point(y, -x, z);
            default -> null;
        };
    }

    public int distance(Point p) { // Manhattan distance
        return Math.abs(p.x - x) + Math.abs(p.y - y) + Math.abs(p.z - z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
