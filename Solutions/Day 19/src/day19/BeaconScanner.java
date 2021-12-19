package day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeaconScanner {
    Point point;
    public int[][] identity;
    public List<Point> beacons = new ArrayList<>();

    public BeaconScanner(List<Point> beacons) {
        this.beacons.addAll(beacons);
    }

    public BeaconScanner(List<Point> beacons, int flip, int rotate) {
        for (Point p : beacons) {
            this.beacons.add(p.flip(flip).rotate(rotate));
        }
    }

    public BeaconScanner(BeaconScanner scanner) { // For result
        this.beacons = new ArrayList<>(scanner.beacons);
    }

    public int[][] identifier() {
        if (identity == null) {
            identity = new int[beacons.size()][beacons.size()];
            for (int i = 0; i < beacons.size(); i++) {
                for (int j = 0; j < beacons.size(); j++) {
                    identity[i][j] = beacons.get(i).distance(beacons.get(j));
                }
                Arrays.sort(identity[i]);
            }
        }
        return identity;
    }

    public boolean matchingIdentifiers(BeaconScanner scanner) {
        for (int i = 0; i < beacons.size(); i++) {
            for (int j = 0; j < scanner.beacons.size(); j++) {
                var s1 = identifier()[i];
                var s2 = scanner.identifier()[j];
                int x = 0, y = 0, count = 0;
                while (x < s1.length && y < s2.length) {
                    if (s1[x] == s2[y]) {
                        if (count == 11)
                            return true;
                        x++;
                        y++;
                        count++;
                    } else if (s1[x] < s2[y]) {
                        x++;
                    } else if (s1[x] > s2[y]) {
                        y++;
                    }
                }
            }
        }
        return false;
    }

    public BeaconScanner pair(List<BeaconScanner> scanners) {
        for (int i = 0; i < scanners.size(); i++) {
            BeaconScanner scanner = scanners.get(i);
            Point matching = match(scanner);
            if (matching != null) {
                scanner.point = matching;
                return scanner;
            }
        }
        return null;
    }

    public Point match(BeaconScanner scanner) {
        for (int i = 0; i < beacons.size(); i++) {
            for (int j = 0; j < scanner.beacons.size(); j++) {
                var p1 = beacons.get(i);
                var p2 = scanner.beacons.get(j);
                int x = p2.x - p1.x, y = p2.y - p1.y, z = p2.z - p1.z;
                int count = 0;
                for (int m = 0; m < beacons.size(); m++) {
                    if ((count + beacons.size() - m) < 12)
                        break;
                    for (int n = 0; n < scanner.beacons.size(); n++) {
                        var p3 = beacons.get(m);
                        var p4 = scanner.beacons.get(n);
                        if (x + p3.x == p4.x && y + p3.y == p4.y && z + p3.z == p4.z) {
                            if (count == 11) // Matching
                                return new Point(x, y, z);
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void addUniqueBeacons(BeaconScanner scanner, Point relPoint) {
        for (int i = 0; i < scanner.beacons.size(); i++) {
            Point p = scanner.beacons.get(i);
            p = new Point(p.x - relPoint.x, p.y - relPoint.y, p.z - relPoint.z);
            if (!beacons.contains(p))
                beacons.add(p);
        }
    }
}
