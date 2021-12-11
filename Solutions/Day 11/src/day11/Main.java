package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        partOne(getInput());
        partTwo(getInput());
    }

    public static void partOne(Grid grid){
        simulate(grid, 100, "one");
    }

    public static void partTwo(Grid grid){
        simulate(grid, 9999, "two");
    }

    public static void simulate(Grid grid, int steps, String part){
        long flashes = 0, step;
        boolean partTwo = false;
        if(part.equals("two"))
            partTwo = true;
        for(step = 0; step < steps; step++){
            long newFlashes = grid.step();
            flashes += newFlashes;
            if(partTwo){
                if(grid.getNrOfOctopuses() == newFlashes){
                    step++;
                    break;
                }
            }
        }
        System.out.printf("Part %s solution - flashes after %d simulations: %d%n", part, step, flashes);
    }

    public static Grid getInput(){
        Grid grid = new Grid();
        try (Stream<String> lines = Files.lines(Path.of("input.txt"))){
            lines.forEach(line -> grid.addRow(line));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return grid;
    }
}
