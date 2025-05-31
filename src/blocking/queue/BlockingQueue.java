package blocking.queue;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue {
    final int n;
    int size;
    final List<Integer> list;

    public BlockingQueue(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative size is not possible.");
        this.n = n;
        this.size = 0;
        this.list = new ArrayList<>(n);
    }

    public synchronized void enqueue(int element) {
        while (size == n) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("Adding element: %d when size is: %d%n", element, size);
        list.addLast(element);
        size++;
        notify();
    }

    public synchronized int dequeue() {
        while (size == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        size--;
        Integer lastElement = list.getLast();
        list.removeLast();
        System.out.println("Dequeued " + lastElement + " element and size is " + size);

        notify();

        return lastElement;
    }
}
