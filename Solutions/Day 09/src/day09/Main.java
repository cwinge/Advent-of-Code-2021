package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        partOne(getInput());
        partTwo(getInput());
    }

    public static void partOne(Grid grid){
        int sum = 0;
        for(int r = 0; r < grid.getRows(); r++){
            for(int c = 0; c < grid.getCols(); c++){
                if(grid.isLowPoint(r,c))
                    sum += 1 + grid.getHeight(r, c);
            }
        }
        System.out.printf("Part one solution - heightmap lowpoint sum: %d%n", sum);
    }

    public static void partTwo(Grid grid){
        List<Integer> basinSizes = new ArrayList<>();
        for(int r = 0; r < grid.getRows(); r++){
            for(int c = 0; c < grid.getCols(); c++){
                if(grid.isLowPoint(r,c))
                    basinSizes.add(grid.getBasinSize(r, c));
            }
        }
        int sum = basinSizes.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b);
        System.out.printf("Part two solution - sum of 3 largest basins: %d%n", sum);
    }

    public static Grid getInput(){
        Grid grid = new Grid();
        try {
            Files.lines(Path.of("input.txt")).forEach(row -> grid.addRow(row));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return grid;
    }
}
