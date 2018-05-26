import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    /**
     * apply move-to-front encoding, reading from standard input and writing to standard output
     */
    public static void encode() {
        char[] chars = initCharArr();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int indexOfC = getIndexOfChar(chars, c);
            BinaryStdOut.write((char) indexOfC);
            moveCharToFirstPlace(chars, indexOfC);
        }
        BinaryStdOut.flush();
    }

    /**
     * apply move-to-front decoding, reading from standard input and writing to standard output
     */
    public static void decode() {
        char[] chars = initCharArr();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int indexOfC = (int) c;
            BinaryStdOut.write(chars[indexOfC]);
            moveCharToFirstPlace(chars, indexOfC);
        }
        BinaryStdOut.flush();
    }

    private static char[] initCharArr() {
        char[] chars = new char[256];
        for (int i = 0; i < 256; i++) {
            chars[i] = (char) i;
        }
        return chars;
    }

    private static int getIndexOfChar(char[] chars, char c) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c)
                return i;
        }
        return -1;
    }

    private static void moveCharToFirstPlace(char[] chars, int charIndex) {
        for (int i = charIndex; i > 0; i--) {
            char temp = chars[i];
            chars[i] = chars[i - 1];
            chars[i - 1] = temp;
        }
    }

    /**
     * if args[0] is '-', apply move-to-front encoding
     * if args[0] is '+', apply move-to-front decoding
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        } else {
            throw new IllegalArgumentException();
        }
    }
}