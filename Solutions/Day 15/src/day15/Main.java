package day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    public static Coord[][] grid;
    public static int gridWidth = 0;
    public static int gridHeight = 0;

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne(){
        System.out.printf("Part one solution - lowest path risk: %d%n", aStar(false));
    }

    public static void partTwo(){
        System.out.printf("Part two solution - lowest path risk: %d%n", aStar(true));
    }

    public static int aStar(boolean isPartTwo) {
        int height = isPartTwo ? gridHeight * 5 - 1: gridHeight - 1;
        int width = isPartTwo ? gridWidth * 5 - 1: gridWidth - 1;
        Coord start = grid[0][0];
        Coord target = new Coord(height, width, getRisk(height, width));
        var openList = new PriorityQueue<Coord>((a,b) -> Integer.compare(a.risk, b.risk));
        var gScore = new HashMap<Coord, Integer>();
        gScore.put(start, 0);
        openList.add(new Coord(0,0,0));

        while (!openList.isEmpty()) {
            Coord currentNode = openList.poll();
            if (currentNode.equals(target))
                return gScore.get(currentNode);

            for (Coord neighbor : getNeighbors(currentNode, width, height)) {
                int totalWeight = gScore.get(currentNode) + neighbor.risk;
                if (totalWeight < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    gScore.put(neighbor, totalWeight);
                    int h = Math.abs(neighbor.x - target.x) + Math.abs(neighbor.y - target.y);
                    openList.add(new Coord(neighbor.x, neighbor.y, totalWeight + h));
                }
            }
        }
        return -1;
    }

    public static List<Coord> getNeighbors(Coord coord, int width, int height){
        List<Coord> neighbors = new ArrayList<>(4);
        if(coord.x != 0) // N
            neighbors.add(new Coord(coord.x-1, coord.y, getRisk(coord.x-1, coord.y)));
        if(coord.x < height) // S
            neighbors.add(new Coord(coord.x+1, coord.y, getRisk(coord.x+1, coord.y)));
        if(coord.y != 0) // W
            neighbors.add(new Coord(coord.x, coord.y-1, getRisk(coord.x, coord.y-1)));
        if(coord.y < width) // E
            neighbors.add(new Coord(coord.x, coord.y+1, getRisk(coord.x, coord.y+1)));

        return neighbors;
    }

    public static int getRisk(int r, int c) {
        int x = r % gridHeight;
        int y = c % gridWidth;
        int risk = grid[x][y].risk;
        for (; c > y; y += gridWidth ) {
            risk = (risk % 9) + 1;
        }

        for (; r > x; x += gridHeight) {
            risk = (risk % 9) + 1;
        }

        return risk;
    }
    public static void printGrid(){
        for(Coord[] row : grid){
            for(Coord c : row){
                System.out.print(c.risk);
            }
            System.out.println();
        }
    }

    public static void getInput(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            int row = 0;
            int col = 0;
            List<Coord[]> rows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Coord[] curRow = new Coord[line.length()];
                for(col = 0; col < line.length(); col++){
                    curRow[col] = new Coord(row, col, Character.getNumericValue(line.charAt(col)));
                }

                row++;
                rows.add(curRow);
            }

            grid = new Coord[row][col];
            for(int i = 0; i < rows.size(); i++){
                grid[i] = rows.get(i);
            }
            
            gridWidth = col;
            gridHeight = row;
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
