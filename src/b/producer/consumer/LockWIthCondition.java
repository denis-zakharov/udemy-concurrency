package b.producer.consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void producer() throws InterruptedException {
        lock.lock();
        System.out.println("Starting producer...");
        condition.await();
        System.out.println("Resuming producer");
        lock.unlock();
    }

    public void consumer() throws InterruptedException {
        Thread.sleep(2000); // sleep to not get the lock first; t1.start and t2.start can be reordered
        lock.lock();
        System.out.println("Starting consumer...");
        condition.signal();
        lock.unlock();
    }
}

public class LockWIthCondition {
    public static void main(String[] args){
        Worker worker = new Worker();
        Thread t1 = new Thread(() -> {
            try {
                worker.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                worker.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
