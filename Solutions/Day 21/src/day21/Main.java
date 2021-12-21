package day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static int playerPos[] = new int[2];
    public static long playerScore[] = new long[2];
    public static long playerWins[] = new long[2];
    public static int playerTurn = 0;
    public static DeterministicDice die = new DeterministicDice();
    public static Map<Integer, Integer> quantumRolls;

    public static void main(String[] args) {
        getInput();
        partOne(3);
        getInput();
        partTwo();
    }

    public static void partOne(int dieRolls){
        while(playerScore[0] < 1000 && playerScore[1] < 1000){
            int res = playTurn(dieRolls);
            playerPos[playerTurn] = (playerPos[playerTurn] + res) % 10; // 0-9 => 1-10
            playerScore[playerTurn] += playerPos[playerTurn] + 1;
            playerTurn = (playerTurn + 1) % 2;
        }

        System.out.printf("Part one solution - losing score * rolls = %d%n", Math.min(playerScore[0], playerScore[1]) * die.rolled);
    }

    public static void partTwo(){
        createQuantumRollOutcomes();
        playQuantum(playerPos[0]+1, playerPos[1]+1, 0, 0, 1, 0);
        System.out.printf("Part two solution - max wins: %d%n", Math.max(playerWins[0], playerWins[1]));
    }

    public static void playQuantum(int playerOnePos, int playerTwoPos,int playerOneScore,int playerTwoScore,
                                   long paths,int player){
        if(playerOneScore >= 21){
            playerWins[0] += paths;
            return;
        }
        if(playerTwoScore >= 21){
            playerWins[1] += paths;
            return;
        }

        for(Integer k : quantumRolls.keySet()){
            int newP1Pos = playerOnePos, newP1Score = playerOneScore, newP2Pos = playerTwoPos, newP2Score = playerTwoScore;
            if(player == 0){
                newP1Pos += k;
                if(newP1Pos > 10)
                    newP1Pos -= 10;
                newP1Score += newP1Pos;
            }else{
                newP2Pos += k;
                if(newP2Pos > 10)
                    newP2Pos -= 10;
                newP2Score += newP2Pos;
            }
            playQuantum(newP1Pos, newP2Pos, newP1Score,newP2Score, paths * quantumRolls.get(k) ,(player + 1) % 2 );
        }
    }

    public static void createQuantumRollOutcomes(){
        quantumRolls = new HashMap<>();
        for (int d1 = 1; d1 < 4; d1++) {
            for (int d2 = 1; d2 < 4; d2++) {
                for (int d3 = 1; d3 < 4; d3++) {
                    quantumRolls.merge(d1 + d2 + d3, 1, Integer::sum);
                }
            }
        }
    }

    public static int playTurn(int dieRolls){
        return die.roll(dieRolls);
    }

    public static void getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            playerPos[0] = Integer.parseInt(br.readLine().split(" ")[4]) - 1;
            playerPos[1] = Integer.parseInt(br.readLine().split(" ")[4]) - 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
