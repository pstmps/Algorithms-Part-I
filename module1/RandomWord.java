import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {

        // System.out.println("Please enter a list of words, then press Ctrl-D (Unix) or Ctrl-Z (Windows) to finish.");
        String champion = null;
        int i = 1;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();

            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word;
            }
            i++;
        }

        if (champion != null) {
            StdOut.println(champion);
        }
    }
}