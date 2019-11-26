package b.producer.consumer;

import java.util.ArrayList;
import java.util.List;

class Processor2 {

    private final List<Integer> list = new ArrayList<>();
    private final int LIMIT = 5;
    private final int BOTTOM = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void producer() throws InterruptedException {

        synchronized (lock) {

            while (true) {

                if (list.size() == LIMIT) {
                    System.out.println("Waiting for removing items from the list...");
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    lock.notify(); // will continue to add until the lock is released by lock.wait() above
                }

                Thread.sleep(500);
            }
        }
    }

    public void consumer() throws InterruptedException {

        synchronized (lock) {

            while (true) {

                if (list.size() == BOTTOM) {
                    System.out.println("Waiting for adding items to the list...");
                    lock.wait();
                } else {
                    System.out.println("Removed: " + list.remove(--value));
                    lock.notify(); // will continue to remove until the lock is released by lock.wait() above
                }

                Thread.sleep(500);
            }

        }

    }
}

public class App2 {

    static Processor2 processor = new Processor2();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
