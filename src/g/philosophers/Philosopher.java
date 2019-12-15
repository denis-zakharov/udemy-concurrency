package g.philosophers;

import java.util.Random;

public class Philosopher implements Runnable {
    final private int id;
    final private Chopstick leftChopstick;
    final private Chopstick rightChopstick;
    final private Random random;
    private volatile boolean isFull = false;
    private int eatingCounter = 0;

    public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.random = new Random(id);
    }

    @Override
    public void run() {
        while (!isFull) {
            try {
                think();
                if (leftChopstick.pickUp(this, ChopstickState.LEFT)) {
                    if (rightChopstick.pickUp(this, ChopstickState.RIGHT)) {
                        eat();
                        rightChopstick.putDown(this, ChopstickState.RIGHT);
                    }
                    leftChopstick.putDown(this, ChopstickState.LEFT);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void eat() throws InterruptedException {
        System.out.println(this + " is eating...");
        eatingCounter++;
        Thread.sleep(random.nextInt(1000));
    }

    private void think() throws InterruptedException {
        System.out.println(this + " is thinking...");
        Thread.sleep(random.nextInt(1000));
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                '}';
    }
}
