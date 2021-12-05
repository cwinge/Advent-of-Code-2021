package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        List<Line> lines = getLines();
        partOne(lines);
        partTwo(lines);
    }

    public static void partOne(List<Line> lines){
        solve(lines, true);
    }

    public static void partTwo(List<Line> lines) {
        solve(lines, false);
    }

    public static void solve(List<Line> lines, boolean partOne){
        HashMap<Point, Integer> overlaps = new HashMap<>();
        for(Line line : lines){
            if(line.isHorizontalOrVertical() || !partOne){
                line.getPoints().forEach(p -> overlaps.merge(p, 1, Integer::sum));
            }
        }

        int overlapping = 0;
        for(Point p : overlaps.keySet()){
            if(overlaps.get(p) != 1){
                overlapping++;
            }
        }
        String part = partOne ? "one" : "two";
        System.out.printf("Part %s solution - Number of lines overlapping: %d%n", part, overlapping);
    }

    public static List<Line> getLines(){
        List<Line> lines = new ArrayList<>();
        try {
           lines.addAll(Files.lines(Path.of("input.txt")).map(s -> s.split(" -> ")).map(Line::new).toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return lines;
    }
}
