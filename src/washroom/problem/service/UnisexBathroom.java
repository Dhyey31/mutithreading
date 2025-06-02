package washroom.problem.service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UnisexBathroom {
    enum Gender {NONE, MALE, FEMALE}

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition menQueue = lock.newCondition();
    private final Condition womenQueue = lock.newCondition();

    private Gender currentGender = Gender.NONE;
    private Gender nextTurn = Gender.MALE; // initial turn
    private int peopleInside = 0;
    private final int MAX_CAPACITY = 3;

    private int waitingMen = 0;
    private int waitingWomen = 0;

    public void enterMale(String name) throws InterruptedException {
        lock.lock();
        try {
            waitingMen++;
            while (currentGender == Gender.FEMALE ||
                    peopleInside == MAX_CAPACITY ||
                    (currentGender == Gender.NONE && nextTurn == Gender.FEMALE && waitingWomen > 0)) {
                menQueue.await();
            }
            waitingMen--;
            currentGender = Gender.MALE;
            peopleInside++;
            System.out.println(name + " entered (Male). Inside: " + peopleInside);
        } finally {
            lock.unlock();
        }
    }

    public void leaveMale(String name) {
        lock.lock();
        try {
            peopleInside--;
            System.out.println(name + " left (Male). Inside: " + peopleInside);
            if (peopleInside == 0) {
                currentGender = Gender.NONE;
                // flip turn
                if (waitingWomen > 0) {
                    nextTurn = Gender.FEMALE;
                    womenQueue.signalAll();
                } else {
                    nextTurn = Gender.MALE;
                    menQueue.signalAll();
                }
            } else {
                menQueue.signal(); // allow more men
            }
        } finally {
            lock.unlock();
        }
    }

    public void enterFemale(String name) throws InterruptedException {
        lock.lock();
        try {
            waitingWomen++;
            while (currentGender == Gender.MALE ||
                    peopleInside == MAX_CAPACITY ||
                    (currentGender == Gender.NONE && nextTurn == Gender.MALE && waitingMen > 0)) {
                womenQueue.await();
            }
            waitingWomen--;
            currentGender = Gender.FEMALE;
            peopleInside++;
            System.out.println(name + " entered (Female). Inside: " + peopleInside);
        } finally {
            lock.unlock();
        }
    }

    public void leaveFemale(String name) {
        lock.lock();
        try {
            peopleInside--;
            System.out.println(name + " left (Female). Inside: " + peopleInside);
            if (peopleInside == 0) {
                currentGender = Gender.NONE;
                // flip turn
                if (waitingMen > 0) {
                    nextTurn = Gender.MALE;
                    menQueue.signalAll();
                } else {
                    nextTurn = Gender.FEMALE;
                    womenQueue.signalAll();
                }
            } else {
                womenQueue.signal(); // allow more women
            }
        } finally {
            lock.unlock();
        }
    }
}
