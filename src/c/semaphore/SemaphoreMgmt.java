package c.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum Downloader {
    INSTANCE;

    private Semaphore semaphore = new Semaphore(3, true);

    void download(int millis) {
        try {
            semaphore.acquire();
            downloadData(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    private void downloadData(int millis) {
        System.out.println("Downloading the data..." + millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SemaphoreMgmt {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i=0; i<21; i++) {
            executorService.execute(() -> {
                int millis = new Random().nextInt(2000);
                Downloader.INSTANCE.download(millis);
            });
        }
        executorService.shutdown();
        System.out.println("Executor is shut down. Awaiting for completion...");
    }
}
