
import java.util.Iterator;
// package module4;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        if (isEmpty()) last = first;
        else oldFirst.prev = first;

        // if (oldFirst != null) {
        //     oldFirst.prev = first;
        // }

        // if (last == null) {
        //     last = first;
        // }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (isEmpty()) first = last;
        else oldLast.next = last;

        // if (oldLast != null) {
        //     oldLast.next = last;
        // }

        // if (first == null) {
        //     first = last;
        // }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node oldFirst = first;
        Item returnItem = oldFirst.item;

        first = oldFirst.next;
        if (first != null) {
            first.prev = null;
        }

        size--;
        // oldFirst = null;
        return returnItem;

    }

    // // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node oldLast = last;
        Item returnItem = oldLast.item;

        last = oldLast.prev;
        if (last != null) {
            last.next = null;
        }

        size--;
        // oldLast = null;
        return returnItem;
    }




    // // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // // unit testing (required)
    public static void main(String[] args) {
        // call directly every public constructor and method to help verify that they work as prescribed (e.g., by printing results to standard output).
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);

        System.out.println("Size: " + deque.size());
        System.out.println("First: " + deque.removeFirst());
        System.out.println("Last: " + deque.removeLast());
        System.out.println("Size: " + deque.size());

        Deque<String> deque2 = new Deque<String>();

        deque2.addFirst("hamburger");
        deque2.addFirst("the");
        deque2.addFirst("is");
        deque2.addFirst("that");
        deque2.addFirst("be");
        deque2.addFirst("to");
        deque2.addFirst("not");
        deque2.addFirst("or");
        deque2.addFirst("be");
        deque2.addFirst("to");

        System.out.println("Size: " + deque2.size());
        // System.out.println("First: " + deque2.removeFirst());

        for (String s : deque2) {
            System.out.println(s);
        }

        System.out.println("Lastword: " + deque2.removeLast());

        deque2.addLast("question");

        for (String s : deque2) {
            System.out.println(s);
        }

    }

}