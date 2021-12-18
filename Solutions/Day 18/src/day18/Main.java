package day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static List<List<Node>> input;

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne(){
        int magnitude = 0;
        List<Node> sum = input.get(0);
        for(int i = 1; i < input.size(); i++){
            sum = add(sum, input.get(i));
            reduce(sum);
        }
        magnitude = magnitude(sum);
        System.out.printf("Part one solution - magnitude: %d%n", magnitude);
    }

    public static void partTwo(){
        int magnitude = 0, maxMagnitude = 0;
        for(int i = 0; i < input.size(); i++){
            for(int j = 0; j < input.size(); j++){
                getInput(); // ugly quickfix instead of refactoring properly
                if(i == j)
                    continue;
                List<Node> sum = add(input.get(i), input.get(j));
                reduce(sum);
                magnitude = magnitude(sum);
                maxMagnitude = magnitude > maxMagnitude ? magnitude : maxMagnitude;
            }
        }
        System.out.printf("Part two solution - Maximum of 2 lines added: %d%n", maxMagnitude);
    }

    public static List<Node> add(List<Node> first, List<Node> second){
        if (first.isEmpty())
            return second;
        first.addAll(second);
        first = first.stream().peek(n -> n.depth++).collect(Collectors.toList());
        return first;
    }

    public static void reduce(List<Node> nodes){
        boolean changed = false;
        do {
            changed = explode(nodes);
            if (changed) continue;
            changed = split(nodes);
        } while (changed);
    }

    private static boolean explode(List<Node> list) {
        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).depth != 4)
                continue;
            Node left = list.get(i);
            Node right = list.get(i + 1);
            int depth = left.depth - 1;
            if (i != 0)
                list.get(i - 1).value += left.value;
            if (i < list.size() - 2)
                list.get(i + 2).value += right.value;
            list.remove(i);
            list.remove(i);
            list.add(i, new Node(depth));
            return true;
        }
        return false;
    }

    private static boolean split(List<Node> list) {
        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).value > 9) {
                list.add(i+1, new Node((int)Math.floor(list.get(i).value / 2.0), list.get(i).depth + 1));
                list.add(i + 2, new Node((int)Math.ceil(list.get(i).value / 2.0), list.get(i).depth + 1));
                list.remove(i);
                return true;
            }
        }
        return false;
    }

    private static int magnitude(List<Node> list) {
        for (int depth = 3; depth >= 0; depth--) {
            for(int i = 0; i < list.size(); i++){
                if(list.get(i).depth == depth) {
                    int newValue = list.get(i).value * 3 + list.get(i+1).value * 2;
                    list.set(i, new Node(newValue, list.get(i).depth - 1));
                    list.remove(i+1);
                }
            }
        }
        return list.get(0).value;
    }

    public static void getInput(){
        input = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Node> row = new ArrayList<>();
                int depth = -1;
                for(char c : line.toCharArray()) {
                    switch(c) {
                        case '[' -> depth++;
                        case ']' -> depth--;
                        case ',' -> {continue;}
                        default -> {
                            row.add(new Node(Character.getNumericValue(c), depth));
                        }
                    }
                }
                input.add(row);
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
