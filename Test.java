import java.util.*;
import java.util.Random;

public class Test{

    public static void main(String[] args) {
        
        Random rand = new Random();
    

        for (int i =0; i<10; i++){
            int randomNum = rand.nextInt((10 - 0) + 1) + 0;
            System.out.println(randomNum);
        }
        
    }
}