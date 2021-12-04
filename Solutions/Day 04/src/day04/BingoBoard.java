package day04;

import java.util.Arrays;
import java.util.Scanner;

public class BingoBoard {
    private Square board[][] = new Square[5][5];
    private boolean won = false;

    public BingoBoard(Scanner scanner){
        for(int r = 0; r < 5; r++){
            for(int c = 0; c < 5; c++){
                board[r][c] = new Square(scanner.nextInt(), false);
            }
        }
    }

    public void mark(int number){
        for (Square[] row : board){
            for(Square square : row){
                if(square.number == number){
                    square.marked = true;
                }
            }
        }
    }

    public boolean isWinner(){
        if(won == true){return true;}
        boolean winner;
        //horizontal
        for(int r = 0; r < 5; r++){
            winner = true;
            for(int c = 0; c < 5; c++){
                if(!board[r][c].marked){
                    winner = false;
                    break;
                }
            }
            if(winner){won = true; return true;}
        }
        //vertical
        for(int c = 0; c < 5; c++){
            winner = true;
            for(int r = 0; r < 5; r++){
                if(!board[r][c].marked){
                    winner = false;
                    break;
                }
            }
            if(winner){won = true; return true;}
        }
        //diagonal - didn't count, oops
        /* boolean descending = true, ascending = true;
        for(int i = 0; i < 5; i++){
            if(!board[i][i].marked){descending = false;}
            if(!board[4-i][i].marked){ascending = false;}
        }
        if(descending || ascending){
            won = true;
            return true;
        }*/
        return false;
    }

    public int calculateScore(){
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(square -> !square.marked)
                .mapToInt(Square::getNumber)
                .sum();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Square row[] : board){
            for(Square square : row){
                if(square.marked){
                    sb.append(" (" + square.number + ") ");
                }else{
                    sb.append(" " + square.number + " ");
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    private class Square{
        public int number;
        public boolean marked;
        public Square(int number, boolean marked){
            this.number = number;
            this.marked = marked;
        }
        public int getNumber(){
            return number;
        }
    }
}
