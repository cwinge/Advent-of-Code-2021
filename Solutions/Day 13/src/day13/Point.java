package day13;

public class Point {
    public int x;
    public int y;
    public static int width = 0;
    public static int height = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        if(x > width)
            width = x;
        if(y > height)
            height = y;
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
        return p.x == x && p.y == y;
    }
}
