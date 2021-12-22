package day22;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static List<String> bootSequence;
    public static List<Cube> cubes;

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne() {
        boot(true);
    }

    public static void partTwo() {
        boot(false);
    }

    public static void boot(boolean partOne) {
        cubes = new ArrayList<>();
        for (String line : bootSequence) {
            Cube cube = getCubeFromInput(line);
            if (partOne) {
                if (cube.xMin < -50 || cube.xMax > 50 || cube.yMin < -50 || cube.yMax > 50
                        || cube.zMin < -50 || cube.zMax > 50) break; // part one cutoff
            }
            List<Cube> newCubes = new ArrayList<>();
            for (Cube c : cubes) {
                if (cube.isIntersecting(c)) {
                    Cube intersection = c.getIntersection(cube, !c.isOn);
                    newCubes.add(intersection);
                }
            }

            cubes.addAll(newCubes);
            if (cube.isOn)
                cubes.add(cube);
        }
        System.out.printf("Part %s solution: number of cubes on: %d%n", (partOne ? "one" : "two"), cubes.stream().mapToLong(Cube::size).sum());
    }

    public static Cube getCubeFromInput(String line) {
        boolean isOn = line.split(" ")[0].equals("on");
        var numbers = line.replaceAll("[^0-9.\\-,]", "").split(",");
        var x = numbers[0].split("\\.\\.");
        var y = numbers[1].split("\\.\\.");
        var z = numbers[2].split("\\.\\.");
        return new Cube(isOn, Integer.parseInt(x[0]), Integer.parseInt(x[1]), Integer.parseInt(y[0]),
                Integer.parseInt(y[1]), Integer.parseInt(z[0]), Integer.parseInt(z[1]));
    }

    public record Cube(boolean isOn, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        public Cube getIntersection(Cube cube, boolean state) {
            return new Cube(state, Math.max(this.xMin, cube.xMin), Math.min(this.xMax, cube.xMax),
                    Math.max(this.yMin, cube.yMin), Math.min(this.yMax, cube.yMax),
                    Math.max(this.zMin, cube.zMin), Math.min(this.zMax, cube.zMax));
        }

        public boolean isIntersecting(Cube cube) {
            return this.xMin <= cube.xMax && this.xMax >= cube.xMin &&
                    this.yMin <= cube.yMax && this.yMax >= cube.yMin &&
                    this.zMin <= cube.zMax && this.zMax >= cube.zMin;
        }

        public long size() {
            return (isOn ? 1 : -1) * (xMax - xMin + 1L) * (yMax - yMin + 1) * (zMax - zMin + 1);
        }
    }

    public static void getInput() {
        try (Stream<String> lines = Files.lines(Paths.get("input.txt"))) {
            bootSequence = lines.collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
