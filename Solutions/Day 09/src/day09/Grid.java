package day09;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
    ArrayList<ArrayList<Coord>> grid;
    int rows = 0;
    int cols = 0;

    public Grid(){
        grid = new ArrayList<ArrayList<Coord>>();
    }

    public void addRow(String row){
        ArrayList<Coord> list = new ArrayList<>();
        for(int i = 0; i < row.length(); i++){
            list.add(new Coord(rows, i, Character.getNumericValue(row.charAt(i))));
        }
        for(char c : row.toCharArray()){

        }
        grid.add(list);
        if(cols == 0)
            cols = row.length();
        rows++;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(ArrayList<Coord> list : grid){
            for(Coord p : list){
                sb.append(p);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isLowPoint(int row, int col){
        boolean ans = true;
        if(col != 0 && grid.get(row).get(col).height >= grid.get(row).get(col-1).height) // left
            ans = false;
        if(row != 0 && grid.get(row).get(col).height >= grid.get(row-1).get(col).height) // above
            ans = false;
        if(col != cols-1 && grid.get(row).get(col).height >= grid.get(row).get(col+1).height) // right
            ans = false;
        if(row != rows-1 && grid.get(row).get(col).height >= grid.get(row+1).get(col).height) // below
            ans = false;
        return ans;
    }

    public int getBasinSize(int row, int col){
        Set<Coord> included = new HashSet<>();
        included.addAll(getBasin(getCoord(row,col)));

        return included.size();
    }

    private Set<Coord> getBasin(Coord coord){
        Set<Coord> basin = new HashSet<>();
        if(coord.height < 9){
            basin.add(coord);
            for(Coord neighbour : getSurroundingCoords(coord)){
                if(coord.height < neighbour.height)
                    basin.addAll(getBasin(neighbour));
            }
        }

        return basin;
    }

    private List<Coord> getSurroundingCoords(Coord coord){
        List<Coord> list = new ArrayList<>();
        if(coord.col != 0) // left
            list.add(getCoord(coord.row, coord.col-1));
        if(coord.row != 0) // above
            list.add(getCoord(coord.row-1, coord.col));
        if(coord.col != cols-1) // right
            list.add(getCoord(coord.row, coord.col+1));
        if(coord.row != rows-1) // below
            list.add(getCoord(coord.row+1, coord.col));

        return list;
    }

    public Coord getCoord(int row, int col){
        return grid.get(row).get(col);
    }

    public Integer getHeight(int row, int col){
        return grid.get(row).get(col).height;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private static class Coord{ // Had to add for part 2
        public int row;
        public int col;
        public int height;
        public Coord(int row, int col, int height){
            this.row = row;
            this.col = col;
            this.height = height;
        }

        @Override
        public boolean equals(Object o){
            if(o == this) { return true;}
            if(!(o instanceof Coord)){ return false;}
            Coord p = (Coord) o;
            return p.row == row && p.col == col;
        }
    }
}
