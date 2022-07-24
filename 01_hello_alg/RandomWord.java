/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        Integer i = 1;
        String str = "";
        String ch = "";
        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                ch = str;
            }
            i = i + 1;
        }
        System.out.println(ch);
    }
}
