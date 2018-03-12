import edu.princeton.cs.algs4.StdRandom;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class SampleItem {
    private String str;
    private Integer intNum;
    private Double floatNum;
    private List<String> strList;

    public SampleItem() {
        this.str = randStr();
        this.intNum = randInt();
        this.floatNum = randDouble();
        this.strList = randStrList();
    }

    private String randStr() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    private Integer randInt() {
        return StdRandom.uniform(512);
    }

    private Double randDouble() {
        return StdRandom.uniform();
    }

    private List<String> randStrList() {
        List<String> strList = new ArrayList<>();
        int n = randInt();
        for (int i = 0; i < n; i++) {
            strList.add(randStr());
        }
        return strList;
    }

    public static List<SampleItem> genSampleList(int n) {
        List<SampleItem> sampleItemList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            SampleItem sampleItem = new SampleItem();
            sampleItemList.add(sampleItem);
        }

        return sampleItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleItem)) return false;

        SampleItem that = (SampleItem) o;

        if (!str.equals(that.str)) return false;
        if (!intNum.equals(that.intNum)) return false;
        if (!floatNum.equals(that.floatNum)) return false;
        return strList.equals(that.strList);
    }

    @Override
    public int hashCode() {
        int result = str.hashCode();
        result = 31 * result + intNum.hashCode();
        result = 31 * result + floatNum.hashCode();
        result = 31 * result + strList.hashCode();
        return result;
    }
}
