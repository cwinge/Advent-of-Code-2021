package day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static List<String> image = new ArrayList<>();
    public static char[] imageEnhanceAlgorithm;

    public static void main(String[] args) {
        getInput();
        partOne();
        partTwo();
    }

    public static void partOne(){
        generateNewImage(2);
        System.out.printf("Part one solution - num lit pixels after 2 generations: %d%n",getLitPixelCount());
    }

    public static void partTwo(){
        generateNewImage(48); // 2 already performed in part one
        System.out.printf("Part two solution - num lit pixels after 50 generations: %d%n",getLitPixelCount());
    }

    public static long getLitPixelCount(){
        return image.stream().mapToLong(s -> s.chars().filter(c -> c == '#').count()).sum();
    }

    public static void generateNewImage(int iterations){
        for(int i = 0; i < iterations; i++){
            List<String> newImage = new ArrayList<>();
            for(int x = -1; x < image.size() + 1; x++){
                StringBuilder sb = new StringBuilder();
                for(int y = -1; y < image.get(0).length() + 1; y++){
                    if(lightPixel(x, y, i))
                        sb.append('#');
                    else
                        sb.append('.');
                }
                newImage.add(sb.toString());
            }
            image = newImage;
        }
    }

    public static boolean lightPixel(int x, int y, int iteration){
        if(imageEnhanceAlgorithm[getPixelNum(x, y, iteration)] == '#')
            return true;
        return false;
    }

    public static boolean isOutside(int x, int y){
        return x < 0 || x >= image.size() || y < 0 || y >= image.size();
    }

    public static int getPixelNum(int x, int y, int iteration){
        int result = 0;
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                if(isOutside(i, j)){
                    if(iteration % 2 == 1){
                        result = (result << 1) | 1;
                    }else{
                        result <<= 1;
                    }
                }else{
                    if(image.get(i).charAt(j) == '#')
                        result = (result << 1) | 1;
                    else
                        result <<= 1;
                }
            }
        }
        return result;
    }

    public static void getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line = br.readLine();
            imageEnhanceAlgorithm = line.toCharArray();
            br.readLine();
            int x = 0;
            while ((line = br.readLine()) != null) {
                image.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
