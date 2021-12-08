package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        partOne(getInput());
        partTwo(getInput());
    }

    public static void partOne(List<Line> lines){
        int amount = 0;
        for(Line line : lines){
            amount += amountOf1478s(line.getDisplay());
        }
        System.out.printf("Part one solution - Amount of 1, 4, 7 and 8s: %d%n", amount);
    }

    public static void partTwo(List<Line> lines){
        long result = 0;
        for(Line line : lines){
            String[] decoded = new String[10];
            decoded[1] = line.getEntries().stream().filter(s -> s.length() == 2).findFirst().get();
            decoded[4] = line.getEntries().stream().filter(s -> s.length() == 4).findFirst().get();
            decoded[7] = line.getEntries().stream().filter(s -> s.length() == 3).findFirst().get();
            decoded[8] = line.getEntries().stream().filter(s -> s.length() == 7).findFirst().get();

            for(String entry : line.getEntries()){
                if(entry.length() == 5){ // can be 2, 3 or 5
                    if(isDecodeSolution(entry, decoded[1])){ // 3
                        decoded[3] = entry;
                    }else if(isDecodeSolution(entry, removeCharacters(decoded[4], decoded[1]))){ // 5
                        decoded[5] = entry;
                    }else{ // 2
                        decoded[2] = entry;
                    }
                }else if(entry.length() == 6){ // can be 0, 6 or 9
                    if(isDecodeSolution(entry, decoded[4])){ // 9
                        decoded[9] = entry;
                    }else if(isDecodeSolution(entry, removeCharacters(decoded[4], decoded[1]))){ // 6
                        decoded[6] = entry;
                    }else{ // 2
                        decoded[0] = entry;
                    }
                }
            }
            result += getDecodedDisplay(line.getDisplay(), decoded);
        }
        System.out.printf("Part two solution - Total: %d%n", result);
    }

    private static long getDecodedDisplay(List<String> display, String[] decoded) {
        StringBuilder sb = new StringBuilder();
        for(String dNum : display){
            for(int i = 0; i < decoded.length; i++){
                if(decoded[i].equals(dNum))
                    sb.append(i);
            }
        }
        return Long.parseLong(sb.toString());
    }

    public static boolean isDecodeSolution(String s1, String s2){
        return removeCharacters(s1, s2).length() == s1.length() - s2.length();
    }

    public static String removeCharacters(String s1, String s2){
        return s1.replaceAll("[" + s2 + "]", "");
    }

    public static int amountOf1478s(List<String> strings){
        int amount = 0;
        for(String s : strings){
            if(s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
                amount++;
        }
        return amount;
    }

    public static List<Line> getInput(){
        List<Line> lines = new ArrayList<>();
        try {
            lines.addAll(Files.lines(Path.of("input.txt")).map(Line::new).toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return lines;
    }
}
