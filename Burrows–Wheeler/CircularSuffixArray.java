import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {

    private String s;
    private Suffix[] suffixes;
    private int[] suffix;
    private int length;

    /**
     * circular suffix array of s
     *
     * @param s
     */
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        this.length = s.length();
        this.s = s;
        this.suffixes = new Suffix[length];
        for (int i = 0; i < length; i++)
            suffixes[i] = new Suffix(s, i);
        Arrays.sort(suffixes);
        //suffix = new int[length];
        //for (int i = 0; i < length; i++)
        //    suffix[i] = i;
        //sort(suffix, length);
        //for (int i = 0; i < length; i++) {
        //    StdOut.print(suffix[i] + " ");
        //}
        //StdOut.println("");
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        private char charAt(int i) {
            i = index + i;
            if (i < text.length())
                return text.charAt(i);
            else
                return text.charAt(i - text.length());
        }

        public int compareTo(Suffix that) {
            int n = text.length();
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return 0;
        }
    }

    /**
     * LSD radix sort for suffix array
     * --- slower than Array.sort()
     *
     * @param a the array keep start index of suffix
     * @param w the number of characters of original string
     */
    public void sort(int[] a, int w) {
        int n = a.length;
        int R = 256;   // extend ASCII alphabet size
        int[] aux = new int[n];

        for (int d = w - 1; d >= 0; d--) {
            StdOut.println(d);
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R + 1];
            for (int i = 0; i < n; i++)
                //count[a[i].charAt(d) + 1]++;
                count[getCharAt(a[i], d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // move data
            for (int i = 0; i < n; i++)
                //aux[count[a[i].charAt(d)]++] = a[i];
                aux[count[getCharAt(a[i], d)]++] = a[i];

            // copy back
            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }

    private char getCharAt(int index, int i) {
        i = index + i;
        if (i < length)
            return s.charAt(i);
        else
            return s.charAt(i - length);
    }

    /**
     * length of s
     *
     * @return
     */
    public int length() {
        return length;
    }

    /**
     * returns index of ith sorted suffix
     *
     * @param i
     * @return
     */
    public int index(int i) {
        if (i < 0 || i >= length()) throw new IllegalArgumentException();
        return suffixes[i].index;
    }

    public static void main(String[] args) {

    }
}