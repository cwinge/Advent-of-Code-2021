package day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static List<BeaconScanner> scanners = new ArrayList<>();
    public static List<List<BeaconScanner>> scannerPositions = new ArrayList<>();
    // Pairs together the result per scanner with correct alignments
    public static BeaconScanner[] orientation;
    public static Point[] position;

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne() {
        generatePositions();
        orientation = new BeaconScanner[scannerPositions.size()];
        position = new Point[scannerPositions.size()];

        orientation[0] = scannerPositions.get(0).get(0);
        position[0] = new Point(0, 0, 0);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int i = 0; i < scannerPositions.size(); i++) {
                if (position[i] == null) {
                    if (scannerPositions.get(current).get(0).matchingIdentifiers(scannerPositions.get(i).get(0))) {
                        BeaconScanner pair = orientation[current].pair(scannerPositions.get(i));
                        if (pair != null) {
                            orientation[i] = pair;
                            position[i] = new Point(position[current], pair.point);
                            queue.add(i);
                        }
                    }
                }
            }
        }

        var result = new BeaconScanner(orientation[0]);
        for (int i = 1; i < scanners.size(); i++) {
            result.addUniqueBeacons(orientation[i], position[i]);
        }
        System.out.printf("Part one solution - number of beacons: %d%n", result.beacons.size());
    }

    public static void partTwo() {
        int maxDistance = Integer.MIN_VALUE;
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position.length; j++) {
                int distance = position[i].distance(position[j]);
                maxDistance = Math.max(maxDistance, distance);
            }
        }
        System.out.printf("Part two solution - maximum distance: %d%n", maxDistance);
    }

    public static void generatePositions() {
        for (BeaconScanner s : scanners) {
            scannerPositions.add(createPossiblePositions(s));
        }
    }

    public static List<BeaconScanner> createPossiblePositions(BeaconScanner scanner) {
        List<BeaconScanner> positions = new ArrayList<>();
        for (int f = 0; f < 6; f++) {
            for (int r = 0; r < 4; r++) {
                positions.add(new BeaconScanner(scanner.beacons, f, r));
            }
        }
        return positions;
    }

    public static void getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Point> beacons = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty())
                        break;
                    var c = line.split(","); // coords
                    beacons.add(new Point(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2])));
                }
                scanners.add(new BeaconScanner(beacons));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
