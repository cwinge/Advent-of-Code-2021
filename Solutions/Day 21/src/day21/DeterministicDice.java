package day21;

public class DeterministicDice {
    public static long rolled = 0;
    public int counter = 1;
    public DeterministicDice(){

    }

    public int roll(int times){
        int result = 0;
        rolled += times;
        for(int i = 0; i < times; i++){
            result += counter++;
            if(counter == 101)
                counter = 1;
        }
        return result;
    }
}
