import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Derek Garcia
 **/

public class Collisions {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));

        HashMap<String, String> collisions = new HashMap<>();
//        int i =0;
        int cCount = 0;
        while(true){
            int num = new Random().nextInt();
            String hash = Hash.hash(Integer.toString(num));

            if(collisions.containsKey(hash)){
                System.out.println("Collision!");
                System.out.println("1: " + collisions.get(hash));
                System.out.println("2: " + num);
                System.out.println("Hash: " + hash);
                cCount++;
            }
//            i++;
            collisions.put(hash, Integer.toString(num));
            if(cCount == 2)
                break;
        }
        System.out.println("No collisions!");
    }
}
