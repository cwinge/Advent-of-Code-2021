package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public final static Map<Character, Integer> LEFT_SYMBOLS = Map.of(
            '{', 3, '[', 2, '(', 1, '<', 4);
    public final static Map<Character, Integer> RIGHT_SYMBOLS = Map.of(
            '}', 1197, ']' , 57,')', 3,'>', 25137);

    public static void main(String[] args) {
        var input = getInput();
        partOne(input);
        partTwo(input);
    }

    public static void partOne(List<String> input){
        int score = 0;
        List<String> corrupted = new ArrayList<>();
        for(String line : input){
            Deque<Character> stack = new ArrayDeque<>();
            for(char c : line.toCharArray()){
                if(isLeft(c)){
                    stack.push(c);
                }else{
                    if(!matchingLeftToRight(stack.peekFirst(), c)){
                        score += RIGHT_SYMBOLS.get(c);
                        corrupted.add(line);
                        break;
                    }else{
                        stack.pop();
                    }
                }
            }
        }

        input.removeAll(corrupted);
        System.out.printf("Part one solution - score: %d%n", score);
    }

    public static void partTwo(List<String> input){
        List<Long> scores = new ArrayList<>();
        for(String line : input){
            Deque<Character> stack = new ArrayDeque<>();
            for(char c : line.toCharArray()){
                if(isLeft(c)){
                    stack.push(c);
                }else{
                    if(matchingLeftToRight(stack.peekFirst(), c))
                        stack.pop();
                }
            }
            scores.add(getAutoCompleteScore(stack));
        }
        System.out.printf("Part two solution - score: %d%n", scores.stream().sorted().toList().get(scores.size()/2));
    }

    public static Long getAutoCompleteScore(Deque<Character> stack){
        Long score = 0L;
        while(!stack.isEmpty()){
            for(char c : RIGHT_SYMBOLS.keySet()){
                if(matchingLeftToRight(stack.peekFirst(), c)){
                    score = LEFT_SYMBOLS.get(stack.pop()) + score * 5;
                    break;
                }
            }
        }

        return score;
    }

    public static boolean isLeft(char c){
        for(char left : LEFT_SYMBOLS.keySet()){
            if(left == c)
                return true;
        }
        return false;
    }

    public static boolean matchingLeftToRight(char a, char b){
        if(a == '(' && b == ')')
            return true;
        if(a == '{' && b == '}')
            return true;
        if(a == '[' && b == ']')
            return true;
        if(a == '<' && b == '>')
            return true;
        return false;
    }

    public static List<String> getInput(){
        List<String> input = new ArrayList<>();
        try {
            input.addAll(Files.lines(Path.of("input.txt")).toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return input;
    }
}
