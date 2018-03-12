import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {

    private RandomizedQueue<String> rq;

    public Permutation() {
        rq = new RandomizedQueue<>();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        Permutation permutation = new Permutation();
        int k = Integer.parseInt(args[0]);
        permutation.getRandomElement(k);
    }

    private void getRandomElement(int k) {
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            rq.enqueue(str);
        }

        Iterator<String> iter = rq.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(iter.next());
        }
    }
}
