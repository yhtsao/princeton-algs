import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class DequeTest {

    @Test
    public void addFirstRemoveFirst() {
        int testVal = 100;
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(testVal);
        int valFromDeque = deque.removeFirst();
        Assertions.assertEquals(testVal, valFromDeque);
        Assertions.assertEquals(0, deque.size());
        Assertions.assertEquals(true, deque.isEmpty());
    }

    @Test
    public void addLastRemoveLast() {
        int testVal = 100;
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(testVal);
        int valFromDeque = deque.removeLast();
        Assertions.assertEquals(testVal, valFromDeque);
        Assertions.assertEquals(0, deque.size());
        Assertions.assertEquals(true, deque.isEmpty());
    }

    @Test
    public void addFirstRemoveLast() {
        int testVal = 100;
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(testVal);
        int valFromDeque = deque.removeLast();
        Assertions.assertEquals(testVal, valFromDeque);
        Assertions.assertEquals(0, deque.size());
        Assertions.assertEquals(true, deque.isEmpty());
    }

    @Test
    public void addLastRemoveFirst() {
        int testVal = 100;
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(testVal);
        int valFromDeque = deque.removeFirst();
        Assertions.assertEquals(testVal, valFromDeque);
        Assertions.assertEquals(0, deque.size());
        Assertions.assertEquals(true, deque.isEmpty());
    }

    @Test
    public void addItemListFirstAndIterate() {
        int[] testValArr = {4, 6, 10, 99, 45};
        Deque<Integer> deque = new Deque<Integer>();
        for (int testVal : testValArr) {
            deque.addFirst(testVal);
        }
        Assertions.assertEquals(testValArr.length, deque.size());

        Iterator<Integer> iter = deque.iterator();
        int i = testValArr.length - 1;
        while (iter.hasNext()) {
            int valueFromIter = iter.next();
            Assertions.assertEquals(testValArr[i], valueFromIter);
            i--;
        }
    }

    @Test
    public void addItemListLastAndIterate() {
        int[] testValArr = {4, 6, 10, 99, 45};
        Deque<Integer> deque = new Deque<Integer>();
        for (int testVal : testValArr) {
            deque.addLast(testVal);
        }
        Assertions.assertEquals(testValArr.length, deque.size());

        Iterator<Integer> iter = deque.iterator();
        int i = 0;
        while (iter.hasNext()) {
            int valueFromIter = iter.next();
            Assertions.assertEquals(testValArr[i], valueFromIter);
            i++;
        }
    }

    @Test
    //@Disabled
    public void addArrAndRemoveAll() {
        List<Integer> intList = genRandIntArray(100, 1, 65536);
        Deque<Integer> deque = new Deque<Integer>();
        for (Integer testVal : intList) {
            int rand = StdRandom.uniform(2);
            if (rand == 0) {
                deque.addFirst(testVal);
            } else {
                deque.addLast(testVal);
            }
        }
        Assertions.assertEquals(intList.size(), deque.size());
        Assertions.assertEquals(false, deque.isEmpty());
        while (!deque.isEmpty()) {
            int rand = StdRandom.uniform(2);
            if (rand == 0) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        Assertions.assertEquals(true, deque.isEmpty());
        Assertions.assertEquals(0, deque.size());
    }

    @Test
    public void randomOp() {
        List<SampleItem> sampleItemList = SampleItem.genSampleList(256);

        Deque<SampleItem> deque = new Deque<>();
        int i = 0;
        int numOfAdd = 0, numOfRemove = 0;
        while (i < sampleItemList.size()) {
            int op = StdRandom.uniform(4);
            if (op == 0) {
                deque.addFirst(sampleItemList.get(i));
                i++;
                numOfAdd++;
            } else if (op == 1) {
                deque.addLast(sampleItemList.get(i));
                i++;
                numOfAdd++;
            } else if (op == 2 && !deque.isEmpty()) {
                deque.removeFirst();
                numOfRemove++;
            } else if (op == 3 && !deque.isEmpty()) {
                deque.removeLast();
                numOfRemove++;
            }

            Assertions.assertEquals(numOfAdd - numOfRemove, deque.size());
        }

    }

    public static List<Integer> genRandIntArray(int numOfInt, int min, int max) {
        List<Integer> intList = new ArrayList<Integer>();
        for (int i = 0; i < numOfInt; i++) {
            intList.add(StdRandom.uniform(min, max));
        }
        return intList;
    }
}