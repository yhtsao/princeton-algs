import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class UnionFindTest {
    public static void main(String[] args) {
        int n = 3;

        QuickFindUF ufModel = new QuickFindUF(n * n);
        System.out.println(ufModel.count());
        ufModel.union(0, 2);
        System.out.println(ufModel.count());
        ufModel.union(2, 8);
        System.out.println(ufModel.count());
        System.out.println(ufModel.find(2));
        System.out.println(ufModel.connected(1, 5));
        System.out.println(ufModel.connected(1, 8));
    }
}