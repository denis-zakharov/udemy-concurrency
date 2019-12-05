package f.queues;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQ {
    public static void main(String[] args) {
        BlockingQueue<DelayedWorker2> delayedQueue = new DelayQueue<>();
        try {
            delayedQueue.put(new DelayedWorker2("I'm first in and first out", 1000));
            delayedQueue.put(new DelayedWorker2("I'm second in and last out", 6000));
            delayedQueue.put(new DelayedWorker2("I'm third in and second out", 4000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!delayedQueue.isEmpty()) {
            try {
                System.out.println(delayedQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class DelayedWorker2 implements Delayed {
    private final String msg;
    private final long duration;

    public DelayedWorker2(String msg, long duration) {
        this.msg = msg;
        this.duration = System.currentTimeMillis() + duration;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return msg;
    }
}