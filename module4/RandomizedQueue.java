import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] items;



    // construct an empty randomized queue
    // @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null item");
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    // @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Queue underflow");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item item = items[randomIndex];
        items[randomIndex] = items[size-1];
        items[size-1] = null;
        size--;
        if (size > 0 && size == items.length/4) {
            resize(items.length/2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return items[StdRandom.uniformInt(size)];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int initialSize;
        private int[] randomIndices;

        private void initiate() {
            initialSize = size;
            randomIndices = new int[size];
            for (int j = 0; j < size; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        public RandomizedQueueIterator() {
            initiate();
            // initialSize = size;
            // randomIndices = new int[size];
            // for (int j = 0; j < size; j++) {
            //     randomIndices[j] = j;
            // }
            // StdRandom.shuffle(randomIndices);
        }

        public boolean hassizeext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            if (size != initialSize) {
                initiate();
            }
            return items[randomIndices[i++]];
        }
    }
    // // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        System.out.println("Sample: " + rq.sample());
        System.out.println("Dequeue: " + rq.dequeue());
        System.out.println("Size: " + rq.size());
        for (int item : rq) {
            System.out.println(item);
        }
    }

}