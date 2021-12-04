package day04;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<BingoBoard> boards = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        getInput(numbers, boards);

        partOne(boards, numbers);
        partTwo(boards, numbers);
    }

    public static void partOne(List<BingoBoard> boards, List<Integer> numbers){
        for(Integer number : numbers){
            for(BingoBoard board : boards){
                board.mark(number);
                if(board.isWinner()){
                    System.out.printf("Part one solution - final score: %d%n", number*board.calculateScore());
                    return;
                }
            }
        }
    }

    public static void partTwo(List<BingoBoard> boards, List<Integer> numbers){
        List<BingoBoard> winners = new ArrayList<>();
        for(Integer number : numbers){
            for(BingoBoard board : boards){
                board.mark(number);
                if(!winners.contains(board) && board.isWinner()){
                    winners.add(board);
                    if(winners.size() == boards.size()){
                        System.out.printf("Part two solution - final score: %d%n", number*board.calculateScore());
                        return;
                    }
                }
            }
        }
    }

    public static void getInput(List<Integer> numbers, List<BingoBoard> boards){
        try(Scanner scanner = new Scanner(Path.of("input.txt"));){
            numbers.addAll(
                    Arrays.stream(scanner.nextLine().split(","))
                    .mapToInt(Integer::valueOf)
                    .boxed().collect(Collectors.toList()));
            while(scanner.hasNextInt()){
                boards.add(new BingoBoard(scanner));
            }
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
