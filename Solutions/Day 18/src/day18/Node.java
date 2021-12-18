package day18;

public class Node {
    public int value = 0;
    public int depth;

    public Node(int depth) {
        this.depth = depth;
    }

    public Node(int value, int depth) {
        this.value = value;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "{" + value + "," + "depth:" + depth + "}";
    }
}
