package day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static final int[] COST = { 1, 10, 100, 1000 };
    public static void main(String[] args) {
        GameState startState = getInput();
        partOne(startState);
    }

    public static void partOne(GameState state){
        System.out.println(play(state));
    }

    public static int play(GameState state){


    }

    public record GameState(char[] roomA, char[] roomB, char[] roomC, char[] roomD, char[] hall, long  cost){ }

    public static GameState getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            br.readLine();
            br.readLine();
            List<Character> a = new ArrayList<>();
            List<Character> b = new ArrayList<>();
            List<Character> c = new ArrayList<>();
            List<Character> d = new ArrayList<>();
            String line;
            while((line = br.readLine()) != null){
                if(line.charAt(3) == '#')
                    break;
                a.add(line.charAt(3));
                b.add(line.charAt(5));
                c.add(line.charAt(7));
                d.add(line.charAt(9));
            }
            char[] roomA = new char[a.size()];
            char[] roomB = new char[b.size()];
            char[] roomC = new char[c.size()];
            char[] roomD = new char[d.size()];
            char[] hall = new char[11];
            Arrays.fill(hall, 'E');
            for(int i = 0; i < a.size(); i++){
                roomA[i] = a.get(i);
                roomB[i] = b.get(i);
                roomC[i] = c.get(i);
                roomD[i] = d.get(i);
            }
            return new GameState(roomA, roomB, roomC, roomD, hall, 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
