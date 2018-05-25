import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    /**
     * apply Burrows-Wheeler transform, reading from standard input and writing to standard output
     */
    public static void transform() {
        String str = "ABRACADABRA!";
        while (!BinaryStdIn.isEmpty()) {
            str = BinaryStdIn.readString();
        }
        CircularSuffixArray suffixArray = new CircularSuffixArray(str);
        char[] result = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            int index;
            if (suffixArray.index(i) == 0) {
                index = str.length() - 1;
                BinaryStdOut.write(i);
            } else
                index = suffixArray.index(i) - 1;
            result[i] = str.charAt(index);
        }
        for (int i = 0; i < str.length(); i++)
            BinaryStdOut.write(result[i]);
        BinaryStdOut.flush();
    }

    /**
     * apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
     */
    public static void inverseTransform() {
        int first = -1;
        while (!BinaryStdIn.isEmpty()) {
            if (first < 0)
                first = BinaryStdIn.readInt();
            BinaryStdIn.readString();
        }
    }

    /**
     * if args[0] is '-', apply Burrows-Wheeler transform
     * if args[0] is '+', apply Burrows-Wheeler inverse transform
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
