package day06;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        partOne(getInput());
        partTwo(getInput());
    }

    public static void partOne(long[] fishInput){
        runSimulation(fishInput, 80);
    }

    public static void partTwo(long[] fishInput){
        runSimulation(fishInput, 256);
    }

    public static void runSimulation(long[] fishInput, int days){
        for(int i = 0; i < days; i++){
            fishInput[(i + 7) % 9] += fishInput[i % 9];
        }
        System.out.printf("Amount of fish after %d days: %d%n", days, Arrays.stream(fishInput).sum());
    }

    public static long[] getInput(){
        long fishCountedByTimer[] = new long[9];
        try(Scanner scanner = new Scanner(Path.of("input.txt"));){
                    var input = Arrays.stream(scanner.nextLine().split(","))
                            .map(Integer::parseInt)
                            .toList();
                    for(int fish : input){
                        fishCountedByTimer[fish]++;
                    }
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return fishCountedByTimer;
    }
}
