package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static HashMap<String, Character> rules = new HashMap<>();
    public static LinkedHashMap<String, Long> template = new LinkedHashMap<>();

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne() {
        LinkedHashMap<String, Long> polymer = template;

        for (int i = 0; i < 10; i++) {
            polymer = step(polymer);
        }

        var counts = getCharacterCount(polymer);
        var answer = counts.values().stream().max(Comparator.naturalOrder()).get() -
                counts.values().stream().min(Comparator.naturalOrder()).get();
        System.out.printf("Part one solution - Most minus least common: %d%n", answer);
    }

    public static Map<Character, Long> getCharacterCount(LinkedHashMap<String, Long> polymer){
        Map<Character, Long> counts = new HashMap<>();
        for (String s : polymer.keySet()){
            if(counts.isEmpty())
                counts.put(s.charAt(0), polymer.get(s));
            if(!counts.containsKey(s.charAt(1)))
                counts.put(s.charAt(1), polymer.get(s));
            else
                counts.put(s.charAt(1), counts.get(s.charAt(1)) + polymer.get(s));
        }
        return counts;
    }

    public static void partTwo() {
        LinkedHashMap<String, Long> polymer = template;

        for (int i = 0; i < 40; i++) {
            polymer = step(polymer);
        }

        var counts = getCharacterCount(polymer);
        var answer = counts.values().stream().max(Comparator.naturalOrder()).get() -
                counts.values().stream().min(Comparator.naturalOrder()).get();
        System.out.printf("Part two solution - Most minus least common: %d%n", answer);
    }

    public static LinkedHashMap<String, Long> step(LinkedHashMap<String, Long> polymer){
        LinkedHashMap<String, Long> transformed = new LinkedHashMap<>();

        for(String pair : polymer.keySet()){
            String left = pair.charAt(0) + rules.get(pair).toString();
            String right = rules.get(pair).toString() + pair.charAt(1);
            if(transformed.containsKey(left))
                transformed.put(left, transformed.get(left) + polymer.get(pair));
            else
                transformed.put(left, polymer.get(pair));

            if(transformed.containsKey(right))
                transformed.put(right, transformed.get(right) + polymer.get(pair));
            else
                transformed.put(right, polymer.get(pair));
        }
        return transformed;
    }

    public static void getInput(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            List<Character> chars = Arrays.stream(br.readLine().split("")).map(s -> s.charAt(0)).toList();
            for(int i = 0; i < chars.size()-1; i++){
                String s = chars.get(i).toString() + chars.get(i+1).toString();
                if(template.containsKey(s))
                    template.put(s, template.get(s) + 1L);
                else
                    template.put(s, 1L);
            }
            br.readLine();
            while ((line = br.readLine()) != null) {
                var rule = line.split(" -> ");
                rules.put(rule[0], line.charAt(6));
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
