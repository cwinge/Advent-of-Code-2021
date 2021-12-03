package day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> input = getInput();
        partOne(input);
        partTwo();
    }

    public static void partOne(List<String> input){
        int[] bitOccurrence = getBitOccurrence(input);
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        for(int bit : bitOccurrence){
            if(bit >= input.size() / 2){
                gamma.append("1");
                epsilon.append("0");
            }else{
                gamma.append("0");
                epsilon.append("1");
            }
        }

        System.out.printf("Part one solution: Gamma = %d and Epsilon = %d %n",
                Integer.parseInt(gamma.toString(), 2), Integer.parseInt(epsilon.toString(), 2));
    }

    public static void partTwo(){
        List<String> oxygen = getInput();
        List<String> co2 = getInput();

        for(int i = 0; i < co2.get(0).length() && co2.size() != 1; i++) {
            if (mostCommonBitAtIndex(i, co2) == 0) {
                removeStringsByBitAtIndex('0', i, co2);
            } else {
                removeStringsByBitAtIndex('1', i, co2);
            }
        }

        for(int i = 0; i < oxygen.get(0).length() && oxygen.size() != 1; i++){
            if(mostCommonBitAtIndex(i, oxygen) == 0) {
                removeStringsByBitAtIndex('1', i, oxygen);
            }else{
                removeStringsByBitAtIndex('0', i, oxygen);
            }
        }

        int o = Integer.parseInt(oxygen.get(0).toString(), 2);
        int co = Integer.parseInt(co2.get(0).toString(), 2);
        System.out.printf("Part two solution: oxygen = %d co2 = %d LSR = %d", o, co, o*co);
    }

    // returns -1 if even, 0 if most 0's else 1.
    public static int mostCommonBitAtIndex(int index, List<String> list){
        int zero = 0, one = 0;
        for(String str : list){
            if(str.charAt(index) == '1'){one++;}
            else{zero++;}
        }
        if(one == zero){ return -1;}
        else if(one > zero){ return 1;}
        else{ return 0;}
    }

    public static void removeStringsByBitAtIndex(char bit, int index, List<String> list){
        for(int i = list.size() - 1; i > -1 && list.size() != 1; i--){
            if(list.get(i).charAt(index) == bit){
                list.remove(list.get(i));
            }
        }
    }

    public static int[] getBitOccurrence(List<String> input){
        int bitOccurrence[] = new int[input.get(0).length()];
        for(String line : input){
            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) == '1'){
                    bitOccurrence[i]++;
                }
            }
        }
        return bitOccurrence;
    }

    public static List<String> getInput(){
        List<String> input = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String binary;
            while ((binary = br.readLine()) != null) {
                input.add(binary);
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return input;
    }
}
