import java.util.Arrays;

public class CircularSuffixArray {

    private String s;
    private Suffix[] suffixes;

    /**
     * circular suffix array of s
     *
     * @param s
     */
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        int n = s.length();
        this.s = s;
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(s, i);
        Arrays.sort(suffixes);
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        private char charAt(int i) {
            if (index + i < text.length())
                return text.charAt(index + i);
            else
                return text.charAt(index + i - text.length());
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
     * length of s
     *
     * @return
     */
    public int length() {
        return s.length();
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