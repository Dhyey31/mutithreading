import blocking.queue.BlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(5);

        try (ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Runnable> addRunnable = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                int finalI = i;
                addRunnable.add(() -> blockingQueue.enqueue(finalI));
            }

            List<Runnable> removeRunnable = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                removeRunnable.add(blockingQueue::dequeue);
            }

            addRunnable.forEach(executors::execute);
            removeRunnable.forEach(executors::execute);
        }
    }
}