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

        if (size == 0) {
            notifyAll();
        }
        size++;
        list.addFirst(element);
        System.out.printf("Adding element: %d when size is: %d%n", element, size);
    }

    public synchronized int dequeue() {
        while (size == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (size == n) {
            notifyAll();
        }

        size--;
        Integer lastElement = list.getLast();
        list.removeLast();
        System.out.println("Dequeued element " + lastElement + " and size is " + size);


        return lastElement;
    }
}
