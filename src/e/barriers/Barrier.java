package e.barriers;

import java.util.concurrent.*;

public class Barrier {
    public static void main(String[] args) {
        // Barrier can be reused with reset()
        CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("All barrier conditions are done!"));
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<5; i++) {
            executorService.execute(new Task(i, barrier));
        }
        executorService.shutdown();

        System.out.println("End of main statements");
    }
}

class Task implements Runnable {
    final private int id;
    final private CyclicBarrier cyclicBarrier;

    public Task(int id, CyclicBarrier cyclicBarrier) {
        this.id = id;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        doWork();
        try {
            cyclicBarrier.await();
            System.out.println("After await on barrier in [" + id + "]");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
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
