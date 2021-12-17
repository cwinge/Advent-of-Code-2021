package day17;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static int yMin;
    public static int yMax;
    public static int xMin;
    public static int xMax;
    public static int yLimit;

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne(){
        yLimit = Math.abs(yMin) - 1;
        int maxHeight = ((yLimit+1)*yLimit) / 2; // triangular number
        System.out.printf("Part one solution - max height: %d%n", maxHeight);
    }

    public static void partTwo(){
        int passedWithin = 0;
        int xLowerLimit = (int)Math.ceil(Math.sqrt((2*xMin*4 + 1)/4.0) - 0.5); // reverse triangular number(only +)
        for(int yv = yMin; yv <= yLimit; yv++){ // start vel
            for(int xv = xLowerLimit; xv <= xMax; xv++){ // start vel
                int dx = xv, dy = yv, x = 0, y = 0;
                while(x < xMax && y > yMin){
                    x += dx;
                    y += dy;
                    if(x >= xMin && x <= xMax && y >= yMin && y <= yMax){
                        passedWithin++;
                        break;
                    }
                    if(dx > 0)
                        dx--;
                    dy--;
                }
            }
        }
        System.out.printf("Part two solution - Number of times passing within the targeted area: %d%n", passedWithin);
    }

    public static void getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            var in = br.readLine().split(" ");
            var x = in[2].replaceAll("[^0-9\\.]", "").split("\\.\\.");
            var y = in[3].replaceAll("[^0-9\\.\\-]", "").split("\\.\\.");
            yMin = Integer.parseInt(y[0]);
            yMax = Integer.parseInt(y[1]);
            xMin = Integer.parseInt(x[0]);
            xMax = Integer.parseInt(x[1]);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
