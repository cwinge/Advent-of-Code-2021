package day02;

public class Command {
    Main.Direction direction;
    int distance;

    public Command(String input) {
        String[] in = input.split(" ");
        for (Main.Direction eDirection : Main.Direction.values()) {
            if (eDirection.name().equalsIgnoreCase(in[0])) {
                direction = eDirection;
            }
        }
        distance = Integer.parseInt(in[1]);
    }

    public Main.Direction getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }

    public String toString(){
        return direction + " " + distance;
    }
}
