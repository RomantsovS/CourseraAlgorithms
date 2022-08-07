import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> d;

    public RandomizedQueue() {
        d = new Deque<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return d.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return d.size();
    }

    // add the item
    public void enqueue(Item item) {
        if (StdRandom.uniform(2) == 0) {
            d.addLast(item);
        }
        else {
            d.addFirst(item);
        }

    }

    // remove and return a random item
    public Item dequeue() {
        if (StdRandom.uniform(2) == 0) {
            return d.removeFirst();
        }
        else {
            return d.removeLast();
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (StdRandom.uniform(2) == 0) {
            Item i = d.removeLast();
            d.addFirst(i);
            return i;
        }
        else {
            Item i = d.removeFirst();
            d.addLast(i);
            return i;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private final Item[] order;
        private int iterN = 0;

        public ListIterator() {
            order = (Item[]) new Object[size()];
            Deque<Item> iterD = new Deque<Item>();

            for (Item i : d) {
                iterD.addLast(i);
            }

            while (!iterD.isEmpty()) {
                // System.out.println("order[N]: " + N);
                if (StdRandom.uniform(2) == 0) {
                    // System.out.println("removeFirst");
                    order[iterN] = iterD.removeFirst();
                }
                else {
                    // System.out.println("removeLast");
                    order[iterN] = iterD.removeLast();
                }
                ++iterN;
            }
        }

        public boolean hasNext() {
            return iterN > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return order[--iterN];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> di = new RandomizedQueue<Integer>();
        di.enqueue(0);
        di.enqueue(1);
        di.enqueue(2);
        di.enqueue(3);
        for (Integer s : di)
            StdOut.println(s);
        StdOut.println("---");

        StdOut.print(di.sample());
        StdOut.print(di.sample());
        StdOut.print(di.sample());
        StdOut.print(di.sample());
        StdOut.print(di.sample());
    }

}
