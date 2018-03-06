import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class StdStatTest {
    public static void main(String[] args) {
        double n = 900;
        double[] arr = {(double)487/n, (double)492/n, (double)532/n};

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]); 
        }
        System.out.println(StdStats.mean(arr));
        System.out.println(StdStats.stddev(arr));

        
    }
}