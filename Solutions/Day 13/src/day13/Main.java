package day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    static List<Point> grid = new ArrayList<>();
    static LinkedHashMap<Integer, Fold> instructions = new LinkedHashMap<>();

    enum Fold{
        Y,
        X
    }

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne(){
        Integer inst = 0;
        for(Integer i : instructions.keySet()){
            if(instructions.get(i) == Fold.X){
                foldLeft(i);
            }else{
                foldUp(i);
            }
            inst = i;
            break; // Only first
        }
        instructions.remove(inst);
        System.out.printf("Part one solution - Number of dots: %d%n", grid.size());
    }

    public static void partTwo(){
        for(Integer i : instructions.keySet()){
            if(instructions.get(i) == Fold.X){
                foldLeft(i);
            }else{
                foldUp(i);
            }
        }
        System.out.printf("Part two solution:%n%s%n", gridToString());
    }

    public static String gridToString(){
        boolean dots[][] = new boolean[grid.get(0).height+1][grid.get(0).width+1];
        StringBuilder sb = new StringBuilder();

        for(Point p : grid){
            dots[p.y][p.x] = true;
        }
        for(boolean []row : dots){
            for(boolean p : row){
                sb.append(p ? "█" : " ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void foldUp(int fold){
        List<Point> remove = new ArrayList<>();
        for(Point p : grid){
            if(p.y > fold){
                int y = fold - (p.y - fold) ;
                if(grid.contains(new Point(p.x, y))){
                    remove.add(p);
                }else{
                    p.y = y;
                }
            }
        }
        grid.removeAll(remove);
        updateHeight();
    }

    public static void foldLeft(int fold){
        List<Point> remove = new ArrayList<>();
        for(Point p : grid){
            if(p.x > fold){
                int x = fold - (p.x - fold) ;
                if(grid.contains(new Point(x, p.y))){
                    remove.add(p);
                }else{
                    p.x = x;
                }
            }
        }
        grid.removeAll(remove);
        updateWidth();
    }

    public static void updateWidth(){
        Point p = grid.stream().max(Comparator.comparingInt(Point::getX)).get();
        p.width = p.getX();
    }

    public static void updateHeight(){
        Point p = grid.stream().max(Comparator.comparingInt(Point::getY)).get();
        p.height = p.getY();
    }

    public static void getInput(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while (!(line = br.readLine()).equals("")) {
                var point = line.split(",");
                grid.add(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
            }
            while ((line = br.readLine()) != null) {
                var instr = line.split(" ");
                var fold = instr[2].split("=");
                if(fold[0].equalsIgnoreCase("x")){
                    instructions.put(Integer.parseInt(fold[1]), Fold.X);
                }else{
                    instructions.put(Integer.parseInt(fold[1]), Fold.Y);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
