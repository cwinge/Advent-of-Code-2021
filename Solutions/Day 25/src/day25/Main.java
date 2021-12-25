package day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public enum Point{
        EMPTY,
        RIGHT,
        DOWN;
    }

    public static Point[][] cucumbers;
    public static int maxX, maxY;

    public static void main(String[] args) {
        getInput();
        partOne();

    }

    public static void partOne(){
        long i = 0;
        boolean changing = true;
        printGrid(0L);
        while(changing){
            changing = step();
            i++;
            printGrid(i);
            //if(i > 0)
            //    break;
        }
        System.out.printf("Part one solution - no moves after step: %d%n", i);
    }

    public static void printGrid(long step){
        System.out.println(step + " ****************");
        for(Point[] row : cucumbers){
            for(Point p : row){
                if(p == Point.EMPTY)
                    System.out.print(".");
                else if(p == Point.RIGHT)
                    System.out.print(">");
                else if(p == Point.DOWN)
                    System.out.print("v");
            }
            System.out.println();
        }
    }

    public static boolean step(){
        boolean changed = false;
        changed = moveRight();
        changed = moveDown() ||changed;
        return changed;
    }

    public static boolean moveRight(){
        boolean changed = false;
        for(int x = 0; x < cucumbers.length; x++){
            for(int y = 0; y < cucumbers[0].length; y++){
                if(cucumbers[x][y] != Point.RIGHT)
                    continue;
                if(y == maxY){
                    if(cucumbers[x][0] == Point.EMPTY){
                        //System.out.println("x: " + x + " y: " + y + "[x][0]=" + cucumbers[x][0] + "to RIGHT");
                        cucumbers[x][y] = Point.EMPTY;
                        cucumbers[x][0] = Point.RIGHT;
                        changed = true;
                    }
                }else{
                    if(cucumbers[x][y+1] == Point.EMPTY){
                        //System.out.println("x: " + x + " y: " + y + "[0][y]= " + cucumbers[x][y+1] + " to RIGHT");
                        cucumbers[x][y] = Point.EMPTY;
                        cucumbers[x][y+1] = Point.RIGHT;
                        changed = true;
                    }
                }
           }
        }

        return changed;
    }

    public static boolean moveDown(){
        boolean changed = false, moved = false;
        for(int y = 0; y < cucumbers[0].length; y++) {
            for (int x = 0; x < cucumbers.length; x++) {
                if(cucumbers[x][y] != Point.DOWN || moved){
                    moved = false;
                    continue;
                }

                if (x == maxX) {
                    if (cucumbers[0][y] == Point.EMPTY) {
                //        System.out.println("x: " + x + " y: " + y + "[0][y]= " + cucumbers[0][y] + " to DOWN");
                        cucumbers[x][y] = Point.EMPTY;
                        cucumbers[0][y] = Point.DOWN;
                        changed = true;
                        moved = true;
                        continue;
                    }
                } else {
                    if (cucumbers[x + 1][y] == Point.EMPTY) {
                  //      System.out.println("x: " + x + " y: " + y + "[0][y]= " + cucumbers[x][y+1] + " to DOWN");
                        cucumbers[x][y] = Point.EMPTY;
                        cucumbers[x + 1][y] = Point.DOWN;
                        changed = true;
                        moved = true;
                        continue;
                    }
                }
                moved = false;
            }
        }
        return changed;
    }

    public static void getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input2.txt"))) {
            String line;
            List<Point[]> rows = new ArrayList<>();
            while((line = br.readLine()) != null){
                Point[] points = new Point[line.length()];
                for(int i = 0; i < points.length; i++){
                    char c = line.charAt(i);
                    if(c == '.')
                        points[i] = Point.EMPTY;
                    else if(c == '>')
                        points[i] = Point.RIGHT;
                    else
                        points[i] = Point.DOWN;
                }
                rows.add(points);
            }
            cucumbers = rows.toArray(new Point[][]{});
            maxY = cucumbers[0].length - 1;
            maxX = cucumbers.length - 1;

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
