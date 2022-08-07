import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private Node head = null;
    private Node tail = null;
    private int n = 0;

    // construct an empty deque
    // public Deque() {
    // }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // // return the number of items on the deque
    public int size() {
        return n;
    }

    // // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node t = new Node();
        t.item = item;

        if (isEmpty()) {
            head = t;
            tail = t;
        }
        else {
            head.prev = t;
            t.next = head;
            head = t;
        }
        ++n;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node old = tail;
        tail = new Node();
        tail.item = item;
        tail.next = null;
        if (isEmpty()) head = tail;
        else {
            old.next = tail;
            tail.prev = old;
        }
        ++n;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        Item item = head.item;
        head = head.next;
        if (isEmpty())
            tail = null;
        else
            head.prev = null;
        --n;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        --n;
        if (head == tail) {
            Item item = head.item;
            head = null;
            tail = null;
            return item;
        }
        else {
            Item item = tail.item;
            tail = tail.prev;
            tail.next = null;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> di = new Deque<Integer>();
        System.out.println("is empty: " + di.isEmpty());
        System.out.println("size: " + di.size());
        di.addLast(1);
        System.out.println("is empty: " + di.isEmpty());
        System.out.println("size: " + di.size());
        di.addLast(2);
        di.addFirst(0);
        for (Integer s : di)
            StdOut.println(s);
        StdOut.println(di.removeFirst());
        System.out.println("is empty: " + di.isEmpty());
        System.out.println("size: " + di.size());
        StdOut.println(di.removeLast());
        System.out.println("is empty: " + di.isEmpty());
        System.out.println("size: " + di.size());
        StdOut.println(di.removeFirst());
        System.out.println("is empty: " + di.isEmpty());
        System.out.println("size: " + di.size());
    }
}
