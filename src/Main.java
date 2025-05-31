import blocking.queue.BlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(3);

        Thread t1 = new Thread(() -> blockingQueue.enqueue(1));
        Thread t2 = new Thread(() -> blockingQueue.enqueue(1));
        Thread t3 = new Thread(() -> blockingQueue.enqueue(1));
        Thread t4 = new Thread(() -> blockingQueue.enqueue(1));
        Thread t5 = new Thread(() -> blockingQueue.enqueue(1));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread t6 = new Thread(blockingQueue::dequeue);
        Thread t7 = new Thread(blockingQueue::dequeue);
        Thread t8 = new Thread(blockingQueue::dequeue);
        Thread t9 = new Thread(blockingQueue::dequeue);

        t6.start();
        t7.start();
        t8.start();
        t9.start();
    }
}