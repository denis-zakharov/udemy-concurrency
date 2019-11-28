package d.executor.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Worker implements Callable<String> {
    private int id;

    Worker(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(400);
        System.out.println("Executing " + id);
        return "Id: " + id;
    }
}

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<String>> futures = new ArrayList<>();
        for (int i=0; i<5; i++) {
            Future<String> future = executor.submit(new Worker(i));
            futures.add(future);
        }
        executor.shutdown();
        System.out.println("All tasks have been submitted!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<String> f : futures) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("Failed execution");
                e.printStackTrace();
            }
        }

    }
}

/* Output example
All tasks have been submitted!
Executing 1
Executing 0
Executing 2
Executing 3
Id: 0
Id: 1
Id: 2
Id: 3
Executing 4
Id: 4
 */