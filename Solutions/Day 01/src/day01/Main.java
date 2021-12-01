package day01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Integer> depths = getDepthReadings();
        partOne(depths);
        partTwo(depths);
    }

    public static List<Integer> getDepthReadings(){
        List<Integer> depths = new ArrayList<Integer>();
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
             depths = stream.mapToInt(Integer::valueOf)
                     .boxed()
                     .collect(Collectors.toList());
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return depths;
    }

    public static void partOne(List<Integer> depths){
        int previous = Integer.MAX_VALUE, increases = 0;
        for(Integer depth: depths){
            if(depth > previous){
                increases++;
            }
            previous = depth;
        }
        System.out.printf("Part one: There are %d number of depth increases%n", increases);
    }

    public static void partTwo(List<Integer> depths){
        int previous = Integer.MAX_VALUE, depth, increases = 0, windowSize = 3;
        for(int i = 0; i <= depths.size()-windowSize; i++){
            depth = depths.get(i) + depths.get(i+1) + depths.get(i+2);
            if(depth > previous){
                increases++;
            }
            previous = depth;
        }
        System.out.printf("Part two: There are %d number of depth increases%n", increases);
    }


}
