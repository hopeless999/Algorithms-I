import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {
        this.size = 0;
        this.first = new Node();
        this.last = new Node();
    }

    private class Node {
        Node prev;
        Item item;
        Node next;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        // If the client calls addFirst() with a null argument
        if (item == null) {
            throw new IllegalArgumentException("Null element");
        }

        // If deque is empty, item is both first and last element
        if (this.size == 0) {
            Node temp = new Node();
            temp.item = item;
            temp.prev = temp;
            temp.next = temp;
            this.first = temp;
            this.last = temp;
        } else { // If deque not empty, item is first element and its next element set to the previous first element
            Node temp = new Node();
            temp.item = item;
            temp.next = this.first;
            this.first.prev = temp;
            this.first = temp;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null element");
        }

        // If deque is empty, item is both first and last element
        if (this.size == 0) {
            Node temp = new Node();
            temp.item = item;
            temp.prev = temp;
            temp.next = temp;
            this.first = temp;
            this.last = temp;
        } else { // If deque not empty, item is last element and is set to be the next of previous last element
            Node temp = new Node();
            temp.item = item;
            temp.prev = this.last;
            this.last.next = temp;
            this.last = temp;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        Node temp = this.first;
        this.first = temp.next;
        this.first.prev = new Node();
        this.size--;
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        Node temp = this.last;
        this.last = temp.prev;
        this.last.next = new Node();
        this.size--;
        return temp.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node curr = first;

        public boolean hasNext() { return curr != null; }

        public void remove() { throw new UnsupportedOperationException("Operation unsupported"); }

        public Item next() {
            if (curr.item == null) {
                throw new NoSuchElementException("End of deque");
            } else {
                Item item = curr.item;
                curr = curr.next;
                return item;
            }
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        System.out.println(test.isEmpty());
        test.addLast(2);
        System.out.println(test.removeFirst());
//        test.addFirst(1);
//        test.addFirst(0);
//        test.addLast(3);
//        System.out.println(test.size());
//        System.out.println(test.removeFirst());
//        System.out.println(test.removeLast());
//        Iterator<Integer> it = test.iterator();
//        while (true) {
//            try {
//                System.out.println(it.next());
//            } catch (NoSuchElementException n) {
//                break;
//            }
//        }
//        System.out.println(test.size());
    }
}
