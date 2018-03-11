import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int numOfElem = 0;
    private Node first, last;

    // Node structure in deque
    private class Node {
        Node prev;
        Node next;
        Item val;
    }

    private void initFirstLastPtr(Item item) {
        // initial first node
        first = new Node();
        first.next = null;
        first.prev = null;
        first.val = item;

        // point last to same node
        last = first;

        // increment number of element
        numOfElem++;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (numOfElem == 0);
    }

    // return the number of items on the deque
    public int size() {
        return numOfElem;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            initFirstLastPtr(item);
        } else {
            Node oldFirst = first;
            first = new Node();
            first.val = item;
            first.next = oldFirst;
            first.prev = null;
            oldFirst.prev = first;

            numOfElem++;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            initFirstLastPtr(item);
        } else {
            Node oldLast = last;
            last = new Node();
            last.val = item;
            last.next = null;
            last.prev = oldLast;
            oldLast.next = last;

            numOfElem++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.val;
        first = first.next;
        numOfElem--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.val;
        last = last.prev;
        numOfElem--;
        return item;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node iterPtr = first;

        @Override
        public boolean hasNext() {
            return (iterPtr != null);
        }

        @Override
        public Item next() {
            if (iterPtr == null) {
                throw new NoSuchElementException();
            }
            Item item = iterPtr.val;
            iterPtr = iterPtr.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
