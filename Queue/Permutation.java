import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        int k = Integer.parseInt(args[0]);
        System.out.println("k = " + k);
        while (StdIn.hasNextLine()) {
            System.out.println(StdIn.readString());
        }

    }
}
