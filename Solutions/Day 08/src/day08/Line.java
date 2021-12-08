package day08;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private List<String> entries;
    private List<String> display;
    public Line(String line){
        var entriesDisplay = line.split(" \\| ");
        entries = sortAlphabetically(entriesDisplay[0].split(" "));
        display = sortAlphabetically(entriesDisplay[1].split(" "));
    }

    public List<String> getEntries() {
        return entries;
    }

    public List<String> getDisplay() {
        return display;
    }

    public List<String> sortAlphabetically(String[] strings){
        List<String> list = new ArrayList<>();
        for(String s : strings){
            String r = s.chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
            list.add(r);
        }
        return list;
    }
}
