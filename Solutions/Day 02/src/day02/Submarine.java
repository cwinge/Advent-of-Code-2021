package day02;

public class Submarine{
    public int getDepth() {
        return depth;
    }

    public void increaseDepth(int depth) {
        this.depth += depth;
        this.aim += depth;
    }

    public void decreaseDepth(int depth) {
        this.depth -= depth;
        this.aim -= depth;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void increaseHorizontal(int horizontal) {
        this.horizontal += horizontal;
        this.aimDepth += aim * horizontal;
    }

    public int getAimDepth() {
        return aimDepth;
    }

    int depth = 0;
    int horizontal = 0;
    int aim = 0;

    int aimDepth = 0;
    public Submarine(){}
}