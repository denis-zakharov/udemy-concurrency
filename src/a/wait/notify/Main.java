package a.wait.notify;

class Threads {
    synchronized void thread1() {
        System.out.println("Starting thread1");
        try {
            wait(); // the current thread should be an onwer of project's monitor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread1: after wait");
    }

    synchronized void thread2() {
        System.out.println("Starting thread2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notify(); // the awakened thread1 will not be able to proceed until the thread 2 releases the lock
        System.out.println("Thread2: after notify");
    } // lock is released
}

public class Main {

    public static void main(String[] args) {
        Threads threads = new Threads();
        Thread t1 = new Thread(threads::thread1);
        Thread t2 = new Thread(threads::thread2);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
