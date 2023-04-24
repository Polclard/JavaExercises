package OS;

import java.util.concurrent.Semaphore;

public class SemaphoreClass {

    static class Thread1 extends Thread
    {
        @Override
        public void run() {
            try {
                A();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                C();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            B();
            try {
                A();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Zavrsi Thread1");
        }
    }


    static class Thread2 extends Thread
    {
        @Override
        public void run() {
            try {
                C();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                A();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            B();
            try {
                C();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            B();
            System.out.println("Zavrsi Thread2");
        }
    }

    static Semaphore semaphore = new Semaphore(4); //TODO
    static void A() throws InterruptedException {
        semaphore.acquire();
        System.out.println("A");
    }
    static void B()
    {
        semaphore.release();
        System.out.println("B");
    }
    static void C() throws InterruptedException {
        semaphore.acquire();
        System.out.println("C");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(semaphore.availablePermits());

    }


}
