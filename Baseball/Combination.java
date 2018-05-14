public class Combination {
    public static int exec(int n, int k) {
        if (k == 0 || k == n)
            return 1;
        // store C(n,k) in a matrix
        int[][] c = new int[n + 1][k + 1];
        int i, j;
        for (i = 0; i <= n; i++) {
            // C(i,0) = 1 for i = 0...n
            c[i][0] = 1;
        }
        for (j = 0; j <= k; j++) {
            // if n = 0, C(n,k) = 0 for all 'k'
            c[0][j] = 0;
        }
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= k; j++) {
                if (i == j) {
                    // C(n,n) = 1
                    c[i][j] = 1;
                } else if (j > i) {
                    // case when r > n in C(n,r)
                    c[i][j] = 0;
                } else {
                    // apply the standard recursive formula
                    c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
                }
            }
        }
        return c[n][k];
    }
}
