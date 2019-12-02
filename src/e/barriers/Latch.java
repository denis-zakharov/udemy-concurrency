package e.barriers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Latch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<5; i++) {
            executorService.execute(new Worker(i, countDownLatch));
        }
        executorService.shutdown();

        System.out.println("Before await on the latch");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All prerequisites are done!");
    }
}

class Worker implements Runnable {
    final private int id;
    final private CountDownLatch latch;

    public Worker(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        doWork();
        latch.countDown();
    }

    private void doWork() {
        System.out.println("Doing some work on Worker [" + id + "]");
        try {
            Thread.sleep(2000 * (id*3 % 2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[" + id + "] DONE");
    }
}
