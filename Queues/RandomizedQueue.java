import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] arr;

    // construct an empty randomized queue
    //@SuppressWarnings("unchecked")
    public RandomizedQueue() {
        this.arr = (Item[]) new Object[1];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    private void resize(int capacity) {
        this.arr = Arrays.copyOf(this.arr, capacity);
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null element");
        }

        if (this.size == this.arr.length) {
            resize(2 * this.arr.length);
        }
        this.arr[this.size] = item;
        this.size++;
    }

    private int pickRandomIndex() {
        return StdRandom.uniform(this.size);
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException("Randomized queue is empty");
        }
        int index = pickRandomIndex();
        Item item = this.arr[index];

        // If the element is at the end of array
        if (index == this.size - 1) {
            this.arr[index] = null;
        }
        // Move all items one up, remove item at index
        while (index < this.size - 1) {
            this.arr[index] = this.arr[index + 1];
            index++;
        }

        // Half the array size if array is a quarter full
        if (this.size > 0 && this.size == this.arr.length/4) {
            resize(this.arr.length/2);
        }

        this.size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.size == 0) {
            throw new NoSuchElementException("Randomized queue is empty");
        }

        return this.arr[pickRandomIndex()];
    }

    private class RandomizedIterator implements Iterator<Item> {
        private final Item[] tempArr;
        private int i = 0;

        public RandomizedIterator() {
            tempArr = Arrays.copyOf(arr, size);
            StdRandom.shuffle(tempArr);
        }

        public boolean hasNext() { return i < size; }
        public void remove() { throw new UnsupportedOperationException("Operation unsupported"); }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException("End of queue"); }
            Item temp = tempArr[i];
            i++;
            return temp;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        System.out.println("Empty? " + q.isEmpty());
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        System.out.println("size: " + q.size());
        System.out.println("sample: " + q.sample());
        System.out.println("removed item: " + q.dequeue());
        System.out.println("size after removal: " + q.size());
        Iterator<Integer> it = q.iterator();
        System.out.println(it.hasNext());
        while (true) {
            try {
                System.out.println(it.next());
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }
}
