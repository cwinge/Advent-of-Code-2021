package day07;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {

    public static void main(String[] args) {
        partOne(getInput());
        partTwo(getInput());
    }

    public static void partOne(Map<Long, Long> crabs){
        long minFuel = Long.MAX_VALUE;
        for(long i = getMinKey(crabs); i <= getMaxKey(crabs); i++){
            long fuel = 0;
            for(Long crabPos : crabs.keySet()){
                fuel += Math.abs(i - crabPos) * crabs.get(crabPos);
            }
            if(minFuel > fuel){
                minFuel = fuel;
            }
        }

        System.out.printf("Part one solution - Minimum fuel: %d%n", minFuel);
    }

    public static void partTwo(Map<Long, Long> crabs){
        long minFuel = Long.MAX_VALUE;
        for(long i = getMinKey(crabs); i <= getMaxKey(crabs); i++){
            long fuel = 0;
            for(Long crabPos : crabs.keySet()){
                fuel += sumSequence(Math.abs(i - crabPos)) * crabs.get(crabPos);
            }
            if(minFuel > fuel){
                minFuel = fuel;
            }
        }

        System.out.printf("Part two solution - Minimum fuel: %d%n", minFuel);
    }

    public static long sumSequence(long n){
        return LongStream.range(1, n+1).sum();
    }

    public static Long getMinKey(Map<Long, Long> map){
        return map.keySet().stream().min((entry1, entry2) -> entry1 > entry2 ? 1 : -1).get();
    }

    public static Long getMaxKey(Map<Long, Long> map){
        return map.keySet().stream().max((entry1, entry2) -> entry1 > entry2 ? 1 : -1).get();
    }

    public static Map<Long, Long> getInput(){
        Map<Long, Long> crabs = new HashMap<>();
        try(Scanner scanner = new Scanner(Path.of("input.txt"));){
            crabs = Arrays.stream(scanner.nextLine().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return crabs;
    }
}
