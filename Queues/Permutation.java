import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (true) {
            try {
                q.enqueue(StdIn.readString());
            } catch (NoSuchElementException e) {
                break;
            }
        }

        Iterator<String> it = q.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(it.next());
        }
    }
}
