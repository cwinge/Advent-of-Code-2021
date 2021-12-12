package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        List<Node> nodes = getInput();
        partOne(nodes);
        partTwo(nodes);
    }

    public static void partOne(List<Node> nodes){
        Node start = nodes.get(nodes.indexOf(new Node("start")));
        int answer = search(new HashSet<Node>(), start, false);
        System.out.println(answer);
    }
    public static void partTwo(List<Node> nodes){
        Node start = nodes.get(nodes.indexOf(new Node("start")));
        int answer = search(new HashSet<Node>(), start, true);
        System.out.println(answer);
    }

    public static int search(Set<Node> visited, Node current, boolean canVisitTwice){
        int paths = 0;
        boolean canVisit = canVisitTwice;
        Set<Node> newVisited = new HashSet<>(visited);
        newVisited.add(current);
        for(Node n : current.neighbours){
            if(n.isStart)
                continue;
            if(n.isEnd){
                paths++;
                continue;
            }
            if(!n.isBig && newVisited.contains(n)){
                if(!canVisit)
                    continue;
                canVisit = false;
            }
            paths += search(newVisited, n, canVisit);
            canVisit = canVisitTwice;
        }
        return paths;
    }

    public static List<Node> getInput(){
        List<Node> nodes = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            int i, j;
            while ((line = br.readLine()) != null) {
                var connection = line.split("-");
                var n1 = new Node(connection[0]);
                var n2 = new Node(connection[1]);
                if((i = nodes.indexOf(n1)) != -1){
                    if((j = nodes.indexOf(n2)) != -1){
                        nodes.get(i).addNeighbour(nodes.get(j));
                    }else{
                        nodes.add(n2);
                        nodes.get(i).addNeighbour(n2);
                    }
                }else{
                    if((j = nodes.indexOf(n2)) != -1){
                        n1.addNeighbour(nodes.get(j));
                    }else{
                        nodes.add(n2);
                        n1.addNeighbour(n2);
                    }
                    nodes.add(n1);
                }
                nodes.get(nodes.indexOf(n2)).addNeighbour(nodes.get(nodes.indexOf(n1)));
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return nodes;
    }
}
