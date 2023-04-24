package OS;

import java.util.concurrent.Semaphore;

public class MolekuliCa3N2{

    public static int NUM_RUN = 50;

    public static Semaphore Ca;
    public static Semaphore N;
    public static Semaphore CaHere;
    public static Semaphore canBond;
    public static int counter;
    public static Semaphore finish;
    public static Semaphore leave;
    public static Semaphore lock;


    public static void init() {
        Ca = new Semaphore(3);
        N = new Semaphore(2);
        CaHere = new Semaphore(0);
        canBond = new Semaphore(0);
        lock = new Semaphore(1);
        finish = new Semaphore(0);
        leave = new Semaphore(0);
        counter = 0;

    }


    public static class Ca extends Thread {

        public void bond() {
            System.out.println("Ca is bonding now.");
        }

        public void execute() throws InterruptedException {
//            Ca.acquire();
//            CaHere.release();
//            canBond.acquire();
//            bond();
//            finish.release();
//            leave.acquire();
//            Ca.release();

            Ca.acquire();
            CaHere.release();
            canBond.acquire();
            bond();
            finish.release();
            leave.acquire();
            Ca.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class N extends Thread {

        public void bond() {
            System.out.println("N is bonding now.");
        }

        public void execute() throws InterruptedException {
            N.acquire();
            lock.acquire();
            counter++;
            if(counter == 2)
            {
                counter = 0;
                lock.release();
            }
            else
            {
                lock.release();
                canBond.acquire();
                bond();
                finish.release();
                leave.acquire();
            }
            N.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
