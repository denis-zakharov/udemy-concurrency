package g.philosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {
    final private Lock lock = new ReentrantLock();
    final private int id;

    public Chopstick(int id) {
        this.id = id;
    }

    boolean pickUp(Philosopher philosopher, ChopstickState state) throws InterruptedException {
        if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            System.out.println(philosopher + " picked up " + state + " chopstick.");
            return true;
        }
        return false;
    }

    void putDown(Philosopher philosopher, ChopstickState state) {
        lock.unlock();
        System.out.println(philosopher + " put down " + state + " chopstick.");
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "id=" + id +
                '}';
    }
}
