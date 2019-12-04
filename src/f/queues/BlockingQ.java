package f.queues;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer<T> implements Runnable {
    final private BlockingQueue<T> blockingQueue;
    final private Random random = new Random(42);
    final private T value;
    private int counter;

    public Producer(BlockingQueue<T> blockingQueue, T value) {
        this.blockingQueue = blockingQueue;
        this.value = value;
    }

    @Override
    public void run() {
        while (counter < 100) {
            final int randonWait = random.nextInt(2000);
            try {
                Thread.sleep(randonWait);
                blockingQueue.put(value);
                System.out.println("Put into queue " + value.toString() + " [" + counter + "]");
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class Consumer<T> implements Runnable {
    final private BlockingQueue<T> blockingQueue;
    final private Random random = new Random(24);
    private int counter;

    public Consumer(BlockingQueue<T> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (counter < 100) {
            final int randonWait = random.nextInt(2000);
            try {
                Thread.sleep(randonWait);
                T value = blockingQueue.take();
                System.out.println("Take from queue " + value.toString() + " [" + counter + "]");
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BlockingQ {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Producer<String> producer = new Producer<>(queue, "VALUE");
        Consumer<String> consumer = new Consumer<>(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
