package f.queues;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQ {
    public static void main(String[] args) {
        // holder for types implementing Comparable; also it accepts Comparator in constructor
        BlockingQueue<String> queue = new PriorityBlockingQueue<>(10, Comparator.reverseOrder());

        new Thread(() -> {
            try {
                queue.put("A");
                queue.put("C");
                Thread.sleep(1000);
                queue.put("B");
                queue.put("Z");
                Thread.sleep(1000);
                queue.put("K");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(queue.take());
                System.out.println(queue.take());
                System.out.println(queue.take());
                System.out.println(queue.take());
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
