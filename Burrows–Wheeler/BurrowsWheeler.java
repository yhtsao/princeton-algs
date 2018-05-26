import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    private static class CharacterSort implements Comparable<CharacterSort> {
        private final Character c;
        private final int index;

        private CharacterSort(Character text, int index) {
            this.c = text;
            this.index = index;
        }

        public int compareTo(CharacterSort that) {
            if (this.c == that.c) return 0;  // optimization
            if (this.c < that.c) return -1;
            if (this.c > that.c) return +1;
            return 0;
        }
    }

    /**
     * apply Burrows-Wheeler transform, reading from standard input and writing to standard output
     */
    public static void transform() {
        String str = "";
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
        String input = "";
        while (!BinaryStdIn.isEmpty()) {
            if (first < 0)
                first = BinaryStdIn.readInt();
            input = BinaryStdIn.readString();
        }

        CharacterSort[] chars = new CharacterSort[input.length()];
        for (int i = 0; i < input.length(); i++) {
            chars[i] = new CharacterSort(input.charAt(i), i);
        }
        Arrays.sort(chars);

        int next = first;
        int i = 0;
        while (i < input.length()) {
            BinaryStdOut.write(chars[next].c);
            next = chars[next].index;
            i++;
        }
        BinaryStdOut.flush();
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
