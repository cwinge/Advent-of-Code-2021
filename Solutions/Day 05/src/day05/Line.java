package day05;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Line {
    Point start, end;

    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    public Line(String xy[]){
        var start = xy[0].split(",");
        var end = xy[1].split(",");
        this.start = new Point(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
        this.end = new Point(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
    }

    public boolean isHorizontalOrVertical(){
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    public Stream<Point> getPoints(){
        if(start.getX() == end.getX()){
            if(start.getY() > end.getY()){
                return IntStream.rangeClosed(end.getY(), start.getY()).boxed().map(y -> {return new Point(end.getX(), y);});
            }else{
                return IntStream.rangeClosed(start.getY(), end.getY()).boxed().map(y -> {return new Point(start.getX(), y);});
            }
        }
        if(start.getY() == end.getY()){
            if(start.getX() > end.getX()){
                return IntStream.rangeClosed(end.getX(), start.getX()).boxed().map(x -> {return new Point(x, end.getY());});
            }else{
                return IntStream.rangeClosed(start.getX(), end.getX()).boxed().map(x -> {return new Point(x, start.getY());});
            }
        }
        // Part 2
        List<Point> points = new ArrayList<>();
        int dx = start.getX() < end.getX() ? 1 : -1;
        int dy = start.getY() < end.getY() ? 1 : -1;
        int x = start.getX();
        int y = start.getY();
        while(x != end.getX() && y != end.getY()){
            points.add(new Point(x, y));
            x += dx;
            y += dy;
        }
        points.add(new Point(end.getX(), end.getY()));

        return points.stream();
    }

    @Override
    public String toString() {
        return start + " -> " + end;
    }
}
