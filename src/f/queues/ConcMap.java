package f.queues;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcMap {
    public static void main(String[] args) {
        ConcurrentMap<Integer, String> map = new ConcurrentHashMap<>();

        new Thread(() -> {
            try {
                map.put(1, "one");
                map.put(2, "two");
                Thread.sleep(1000);
                map.put(3, "three");
                map.put(4, "four");
                map.put(5, "five");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(map.get(1));
                System.out.println(map.get(2));
                System.out.println(map.get(3)); // null because t1 has not yet put the value (t1 is sleeping)
                Thread.sleep(3000);
                System.out.println(map.get(4));
                System.out.println(map.get(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
