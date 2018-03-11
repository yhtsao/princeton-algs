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
        return RandomStringUtils.random(256);
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
        if (o == null || getClass() != o.getClass()) return false;

        SampleItem that = (SampleItem) o;

        if (str != null ? !str.equals(that.str) : that.str != null) return false;
        if (intNum != null ? !intNum.equals(that.intNum) : that.intNum != null) return false;
        return floatNum != null ? floatNum.equals(that.floatNum) : that.floatNum == null;
    }

    @Override
    public int hashCode() {
        int result = str != null ? str.hashCode() : 0;
        result = 31 * result + (intNum != null ? intNum.hashCode() : 0);
        result = 31 * result + (floatNum != null ? floatNum.hashCode() : 0);
        return result;
    }
}
