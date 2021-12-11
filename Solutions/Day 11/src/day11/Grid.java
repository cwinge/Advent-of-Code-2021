package day11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grid {
    ArrayList<ArrayList<Octopus>> grid;
    int rows = 0;
    int cols = 0;
    int NrOfOctopuses;

    public Grid(){
        grid = new ArrayList<>();
    }

    public void addRow(String row){
        ArrayList<Octopus> list = new ArrayList<>();
        for(int i = 0; i < row.length(); i++){
            list.add(new Octopus(rows, i, Character.getNumericValue(row.charAt(i))));
        }
        grid.add(list);
        if(cols == 0)
            cols = row.length();
        rows++;
        NrOfOctopuses += row.length();
    }

    public int getNrOfOctopuses() {
        return NrOfOctopuses;
    }

    public int step(){
        Set<Octopus> flashed = new HashSet<>();
        for(var row : grid){
            for(var octopus : row){
                octopus.energy++;
                if(octopus.energy == 10){
                    flash(octopus);
                    flashed.add(octopus);
                }
            }
        }

        while(chainFlashes(flashed));
        resetFlashed(flashed);
        return flashed.size();
    }

    public boolean chainFlashes(Set<Octopus> flashed){
        boolean changed = false;
        for(var row : grid){
            for(var octopus : row){
                if(octopus.energy > 9 && !flashed.contains(octopus)){
                    flash(octopus);
                    flashed.add(octopus);
                    changed = true;
                }
            }
        }
        return changed;
    }

    public void resetFlashed(Set<Octopus> flashed){
        for(var o : flashed){
            o.energy = 0;
        }
    }

    public void flash(Octopus o){
        if(o.col != 0) { // left
            if(o.row != 0) // NW
                getOctopus(o.row-1, o.col-1).energy++;
            getOctopus(o.row, o.col-1).energy++; // W
            if(o.row != rows-1) // SW
                getOctopus(o.row+1, o.col-1).energy++;
        }
        if(o.row != 0) // above
            getOctopus(o.row-1, o.col).energy++; // N
        if(o.col != cols-1) { // right
            if (o.row != 0) // NE
                getOctopus(o.row - 1, o.col + 1).energy++;
            getOctopus(o.row, o.col + 1).energy++; // E
            if (o.row != rows - 1) // SE
                getOctopus(o.row + 1, o.col + 1).energy++;
        }
        if(o.row != rows-1) // below
            getOctopus(o.row+1, o.col).energy++; // S
    }

    public Octopus getOctopus(int row, int col){
        return grid.get(row).get(col);
    }

    private static class Octopus { // Had to add for part 2
        public int row;
        public int col;
        public int energy;
        public Octopus(int row, int col, int energy){
            this.row = row;
            this.col = col;
            this.energy = energy;
        }

        @Override
        public boolean equals(Object o){
            if(o == this) { return true;}
            if(!(o instanceof Octopus)){ return false;}
            Octopus p = (Octopus) o;
            return p.row == row && p.col == col;
        }
    }
}