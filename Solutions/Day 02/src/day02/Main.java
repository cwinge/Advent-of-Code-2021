package day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Command> commands = getCommands();
        partOne(commands);
        partTwo(commands);
    }

    public static void partOne(List<Command> commands){
        Submarine sub = new Submarine();
        for(Command c : commands){
            switch (c.direction){
                case UP:
                    sub.decreaseDepth(c.distance);
                    break;
                case DOWN:
                    sub.increaseDepth(c.distance);
                    break;
                case FORWARD:
                    sub.increaseHorizontal(c.distance);
                    break;
            }
        }
        System.out.printf("Part one solution: depth %d * horizontal %d = %d%n",
                sub.getDepth(), sub.getHorizontal(), sub.getDepth() * sub.getHorizontal());
    }

    public static void partTwo(List<Command> commands){
        Submarine sub = new Submarine();
        for(Command c : commands){
            switch (c.direction){
                case UP:
                    sub.decreaseDepth(c.distance);
                    break;
                case DOWN:
                    sub.increaseDepth(c.distance);
                    break;
                case FORWARD:
                    sub.increaseHorizontal(c.distance);
                    break;
            }
        }
        System.out.printf("Part two solution: aimDepth %d * horizontal %d = %d%n",
                sub.getAimDepth(), sub.getHorizontal(), sub.getAimDepth() * sub.getHorizontal());
    }

    public enum Direction{
        UP,
        DOWN,
        FORWARD;
    }

    public static List<Command> getCommands(){
        List<Command> commands = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String command;
            while ((command = br.readLine()) != null) {
                commands.add(new Command(command));
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return commands;
    }
}


