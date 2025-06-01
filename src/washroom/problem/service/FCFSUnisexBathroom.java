package washroom.problem.service;

import java.util.*;
import java.util.concurrent.locks.*;

public class FCFSUnisexBathroom {
    public enum Gender { MALE, FEMALE }

    private static class EntryRequest {
        final Gender gender;
        final Condition condition;
        boolean granted = false;

        EntryRequest(Gender gender, Condition condition) {
            this.gender = gender;
            this.condition = condition;
        }
    }

    private final ReentrantLock lock = new ReentrantLock(true);
    private final LinkedList<EntryRequest> queue = new LinkedList<>();
    private final int MAX_CAPACITY = 3;

    private int insideCount = 0;
    private Gender currentGender = null;

    public void enter(Gender gender, String name) throws InterruptedException {
        lock.lock();
        try {
            Condition myCond = lock.newCondition();
            EntryRequest myRequest = new EntryRequest(gender, myCond);
            queue.addLast(myRequest);

            while (true) {
                // Must be at the head of the queue
                if (queue.peekFirst() != myRequest ||
                        (insideCount > 0 && currentGender != gender) ||
                        insideCount >= MAX_CAPACITY) {
                    myCond.await();
                } else {
                    // Grant access
                    insideCount++;
                    currentGender = gender;
                    queue.removeFirst();
                    System.out.println(name + " entered (" + gender + "). Inside: " + insideCount);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void leave(Gender gender, String name) {
        lock.lock();
        try {
            insideCount--;
            System.out.println(name + " left (" + gender + "). Inside: " + insideCount);

            if (insideCount == 0) {
                currentGender = null;
            }

            // Wake up all to re-evaluate
            for (EntryRequest req : queue) {
                req.condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
