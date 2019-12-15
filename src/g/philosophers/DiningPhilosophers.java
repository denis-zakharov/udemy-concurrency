package g.philosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiningPhilosophers {
    final private static int SIMULATION_TIME_MS = 100 * 1000;
    final private static int PHILOSOPHERS = 5;
    final private static int CHOPSTICKS = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(PHILOSOPHERS);
        Philosopher[] philosophers = new Philosopher[PHILOSOPHERS];
        for (int i = 0; i < PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, new Chopstick(i), new Chopstick((i + 1) % PHILOSOPHERS));
            executorService.execute(philosophers[i]);
        }
        executorService.shutdown();

        try {
            Thread.sleep(SIMULATION_TIME_MS);
            for (Philosopher philosopher : philosophers) {
                philosopher.setFull(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            executorService.awaitTermination(2 * SIMULATION_TIME_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Termination time exceeded!");
        }
        for (Philosopher philosopher : philosophers) {
            System.out.println(philosopher + " ate #" + philosopher.getEatingCounter() + " times");
        }
    }
}
