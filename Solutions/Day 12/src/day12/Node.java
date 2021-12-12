package day12;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    String name;
    boolean isBig, isStart, isEnd;
    Set<Node> neighbours = new HashSet<>();

    public Node(String name) {
        this.name = name;
        if(Character.isUpperCase(name.charAt(0)))
            isBig = true;
        if(name.equals("start"))
            isStart = true;
        if(name.equals("end"))
            isEnd = true;
    }

    public void addNeighbour(Node n){
        neighbours.add(n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
