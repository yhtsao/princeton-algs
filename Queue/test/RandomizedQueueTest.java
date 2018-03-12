import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

class RandomizedQueueTest {

    @Test
    public void oneElement() {
        RandomizedQueue<SampleItem> rq = new RandomizedQueue<>();
        SampleItem sampleItem = new SampleItem();
        rq.enqueue(sampleItem);
        Assertions.assertEquals(1, rq.size());

        SampleItem temp = rq.dequeue();
        Assertions.assertEquals(sampleItem, temp);
        Assertions.assertEquals(true, rq.isEmpty());
    }

    @Test
    public void enqueue() {
        List<SampleItem> sampleItemList = SampleItem.genSampleList(65536);
        RandomizedQueue<SampleItem> rq = new RandomizedQueue<>();

        int i = 0;
        int numOfElem = 0;
        while (i < sampleItemList.size()) {
            int op = StdRandom.uniform(3);
            if (op == 0) {
                rq.enqueue(sampleItemList.get(i));
                i++;
                numOfElem++;
            } else if (!rq.isEmpty() && op == 1) {
                rq.dequeue();
                numOfElem--;
            } else if (!rq.isEmpty() && op == 2) {
                rq.sample();
            }
        }
        Assertions.assertEquals(numOfElem, rq.size());
    }

    @Test
    public void interatorInteger() {
        List<Integer> intList = DequeTest.genRandIntArray(20, 1, 512);
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        System.out.println("------- Enqueue test -------");
        for (int i = 0; i < intList.size(); i++) {
            System.out.print(intList.get(i) + " ");
            rq.enqueue(intList.get(i));
            Assertions.assertEquals(i + 1, rq.size());
        }
        System.out.println("\n------- Iterator test -------");
        for (int i = 0; i < 5; i++) {
            Iterator<Integer> iter = rq.iterator();
            while (iter.hasNext()) {
                System.out.print(iter.next() + " ");
            }
            System.out.println("");
        }
        System.out.println("------- Dequeue test -------");
        while (!rq.isEmpty()) {
            System.out.println(rq.sample() + " " + rq.dequeue());
        }
    }


}