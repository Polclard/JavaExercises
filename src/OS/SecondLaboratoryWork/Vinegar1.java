package OS.SecondLaboratoryWork;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Vinegar1 {
    static Semaphore cForm;
    static Semaphore hForm;
    static Semaphore oForm;
    static Semaphore ready;
    static Semaphore finished;
    static boolean valid = true;

    static int totalAtoms;
    static Lock lock;
    static int total = 0;

    static void init() {

        oForm = new Semaphore(2);
        hForm = new Semaphore(4);
        cForm = new Semaphore(2);

        ready = new Semaphore(0);
        finished = new Semaphore(0);

        totalAtoms = 0;
        lock = new ReentrantLock();

    }


    public static void main(String[] args) {
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            threads.add(new O());
            threads.add(new H());
            threads.add(new H());
            threads.add(new C());
        }

        init();
        // run all threads in background
        for (Thread t : threads) {
            t.start();
        }
        // after all of them are started, wait each of them to finish for maximum 2_000 ms
        for (Thread t : threads) {
            try {
                t.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class O extends Thread {

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            oForm.acquire();
            System.out.println("O here.");
            lock.lock();
            totalAtoms++;
            if (totalAtoms == 2) {
                valid = true;
                ready.release(8);
            }
            lock.unlock();

            ready.acquire();
            System.out.println("Molecule bonding.");

            lock.lock();
            totalAtoms--;
            if (totalAtoms == 0) {
                finished.release(8);
            }
            lock.unlock();

            finished.acquire();
            System.out.println("O done.");

            lock.lock();
            if (valid) {
                valid = false;
                total++;
                System.out.println("Molecule created.");
            }
            lock.unlock();
            oForm.release();
        }

    }

    static class C extends Thread {
        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException { //4 thread
            cForm.acquire();
            System.out.println("C here.");
            lock.lock();
            totalAtoms++;
            if (totalAtoms == 2) {
                valid = true;
                ready.release(8);
            }
            lock.unlock();

            ready.acquire();
            System.out.println("Molecule bonding.");

            lock.lock();
            totalAtoms--;
            if (totalAtoms == 0) {
                finished.release(8);
            }
            lock.unlock();

            finished.acquire();
            System.out.println("C done.");

            lock.lock();
            if (valid) {
                valid = false;
                total++;
                System.out.println("Molecule created.");
            }
            lock.unlock();
            cForm.release();
        }
    }

    static class H extends Thread {

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            hForm.acquire();
            System.out.println("H here.");
            lock.lock();
            totalAtoms++;
            if (totalAtoms == 4) {
                valid = true;
                ready.release(8);
            }
            lock.unlock();

            ready.acquire();
            System.out.println("Molecule bonding.");

            lock.lock();
            totalAtoms--;
            if (totalAtoms == 0) {
                finished.release(8);
            }
            lock.unlock();

            finished.acquire();
            System.out.println("H done.");

            lock.lock();
            if (valid) {
                valid = false;
                total++;
                System.out.println("Molecule created.");
            }
            lock.unlock();
            hForm.release();
        }

    }


}