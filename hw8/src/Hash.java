import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * HW 8
 * CSCI 462
 * Basic hashing
 *
 * @author Derek Garcia
 **/

public class Hash {

    // Constants
    private final static int M_BLOCK_SIZE = 8;  // 64 bits = 8 bytes
    private final static String SEED_STRING = "10110011110010100010110011110010";
    private static HashMap<Integer, List<Integer>> CONSTANTS;


    /**
     * Load constants
     */
    private static void loadConstants(){
        CONSTANTS = new HashMap<>();
        CONSTANTS.put(0, stringToBits("00000000000000000000000000000011"));
        CONSTANTS.put(1, stringToBits("00000000000000000000000000000101"));
        CONSTANTS.put(2, stringToBits("00000000000000000000000000000111"));
        CONSTANTS.put(3, stringToBits("00000000000000000000000000001011"));
        CONSTANTS.put(4, stringToBits("00000000000000000000000000001101"));
        CONSTANTS.put(5, stringToBits("00000000000000000000000000010001"));
    }

    /**
     * F function
     *
     * @param H previous Hash input
     * @param message message to hash
     * @return new H
     */
    private static List<Integer> F(List<Integer> H, List<Integer> message){

        // Repeat 0 - 5
        for(int i = 0; i < 6; i++){

            // get 1st 32 bits if even, last 32 if odd
            List<Integer> wi = (i % 2 == 0 ? message.subList(0, 32) : message.subList(32, 64));
            List<Integer> a = AND(wi, leftShift(H, 16));
            H = XOR(wi, a);
            H = leftShift(H, i);
            H = XOR(H, CONSTANTS.get(i));
        }
        return H;
    }

    /**
     * Left shift bits
     *
     * @param bits list of bits to shift
     * @param shift shift
     * @return shifted bits
     */
    private static List<Integer> leftShift(List<Integer> bits, int shift){

        for(int i = 0; i < shift; i++){
            int bit = bits.remove(0);   // pop
            bits.add(bit);  // re-add to queue
        }
        return bits;
    }

    /**
     * Ands series of bits together
     *
     * @param a list of bits
     * @param b list of bits
     * @return anded list of bits
     */
    private static List<Integer> AND(List<Integer> a, List<Integer> b){
        // err if size mismatch
        if(a.size() != b.size())
            return null;

        List<Integer> result = new ArrayList<>();    // 32 bit result

        // and bits
        int i = 0;
        for (int bit : a)
            result.add(bit & b.get(i++));

        return result;
    }

    /**
     * XOR series of bits
     *
     * @param a list of bits
     * @param b list of bits
     * @return xored list of bits
     */
    private static List<Integer> XOR(List<Integer> a, List<Integer> b){
        // err if size mismatch
        if(a.size() != b.size())
            return null;

        List<Integer> result = new ArrayList<>();

        // XOR bits
        int i = 0;
        for (int bit : a)
            result.add(bit ^ b.get(i++));

        return result;
    }

    /**
     * Convert a binary string to list of ints
     * @param str string to convert
     * @return list of bits
     */
    private static List<Integer> stringToBits(String str){

        // convert to bit array
        List<Integer> bits = new ArrayList<>();
        for(char c : str.toCharArray())
            bits.add(c == '1' ? 1 : 0);

        return bits;
    }


    /**
     * main driver
     *
     * @param args  arg[0]: word to hash
     * @throws IOException unable to write to byte stream
     */
    public static void main(String[] args) throws IOException {
        loadConstants();

        System.out.println("Your Message: " + args[0]);
        ByteArrayOutputStream msgStream = new ByteArrayOutputStream();

        msgStream.write(args[0].getBytes());


        // pad with 0s until correct size
        while(msgStream.size() % M_BLOCK_SIZE != 0)
            msgStream.write(new byte[1]);

        // convert to bit string
        StringBuilder s = new StringBuilder();
        for(byte b : msgStream.toByteArray())
            s.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));

        msgStream.close();

        // convert to bit llist
        List<Integer> msg = stringToBits(s.toString());
        List<Integer> out = stringToBits(SEED_STRING);     // get given seed


        // As many rounds as needed
        for(int i = 0; i < msg.size(); i += 64) {
            // get the next 64 bits
            List<Integer> block = msg.subList(i, i + 64);

            // XOR result of F and current out
            out = XOR(F(out, block), out);

        }

        // print result
        System.out.print("Hashed: ");
        for(int bit : out)
            System.out.print(bit);

    }
}
