package OS.SecondLaboratoryWork;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;


public class Vinegar {
    //C2H4O2
    // The controller will be C
    public static Semaphore C;
    public static Semaphore H;
    public static Semaphore O;

    public static Semaphore canBond;
    public static Semaphore finish;

    public static Semaphore lock;

    static int totalN;
    static int totalNumOfAtoms = 0;

    static boolean okay = false;

    public static void init()
    {
        C = new Semaphore(2);
        H = new Semaphore(4);
        O = new Semaphore(2);


        canBond = new Semaphore(0);
        finish = new Semaphore(0);

        lock = new Semaphore(1);
        totalNumOfAtoms = 0;
    }

    public static void main(String[] args) throws InterruptedException {



        HashSet<Thread> threads = new HashSet<>();

        for (int i = 0; i < 20; i++) {

            threads.add(new C());

            threads.add(new H());

            threads.add(new H());

            threads.add(new O());

        }

        init();


        // run all threads in background
        for(Thread thread : threads)
        {
            thread.start();
        }


        // after all of them are started, wait each of them to finish for maximum 2_000 ms

        for(Thread thread : threads)
        {
            try{
                thread.join(2000);
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }
        // for each thread, terminate it if it is not finished
        // System.out.println("Possible deadlock!");
    }


    static class C extends Thread{

        @Override
        public void run()
        {
            try{
                execute();
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }

        public void execute() throws InterruptedException {

            // at most 2 atoms should print this in parallel

            C.acquire();
            System.out.println("C here.");
            lock.acquire();
            totalNumOfAtoms++;
            if(totalNumOfAtoms == 2)
            {
                okay = true;
                canBond.release(8);
            }
            lock.release();


            // after all atoms are present, they should start with the bonding process together
            canBond.acquire();
            System.out.println("Molecule bonding.");

//            Thread.sleep(100);// this represents the bonding process

            lock.acquire();
            totalNumOfAtoms--;
            if(totalNumOfAtoms == 0)
            {
                finish.release(8);
            }
            lock.release();

            finish.acquire();
            System.out.println("C done.");


            finish.acquire();
            if(okay)
            {
                okay = false;
                totalN++;
                // only one atom should print the next line, representing that the molecule is created
                System.out.println("Molecule created.");
            }
            lock.release();
            C.release();
        }

    }



    static class H extends Thread{

        @Override
        public void run()
        {
            try{
                execute();
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }

        public void execute() throws InterruptedException {

            // at most 4 atoms should print this in parallel
            H.acquire();
            System.out.println("H here.");
            lock.acquire();
            totalNumOfAtoms++;
            if(totalNumOfAtoms == 4)
            {
                okay = true;
                canBond.release(8);
            }
            lock.release();
            // after all atoms are present, they should start with the bonding process together
            canBond.acquire();
            System.out.println("Molecule bonding.");

//            Thread.sleep(100);// this represents the bonding process

            lock.acquire();
            totalNumOfAtoms--;
            if(totalNumOfAtoms == 0)
            {
                finish.release(8);
            }
            lock.release();

            finish.acquire();
            System.out.println("H done.");

            // only one atom should print the next line, representing that the molecule is created
            lock.acquire();
            if(okay)
            {
                okay = false;
                totalN++;
                System.out.println("Molecule created.");
            }
            lock.release();
            H.release();
        }

    }



    static class O extends Thread{

        @Override
        public void run()
        {
            try{
                execute();
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }

        public void execute() throws InterruptedException {

            // at most 2 atoms should print this in parallel

            O.acquire();
            System.out.println("O here.");
            lock.acquire();
            totalNumOfAtoms++;
            if(totalNumOfAtoms == 2)
            {
                okay = true;
                canBond.release(8);
            }
            lock.release();
            // after all atoms are present, they should start with the bonding process together
            canBond.acquire();
            System.out.println("Molecule bonding.");

//            Thread.sleep(100);// this represents the bonding process

            lock.acquire();
            totalNumOfAtoms--;
            if(totalNumOfAtoms == 0)
            {
                finish.release(8);
            }
            lock.release();

            finish.acquire();
            System.out.println("O done.");


            lock.acquire();
            if(okay)
            {
                okay = false;
                totalN++;
                // only one atom should print the next line, representing that the molecule is created
                System.out.println("Molecule created.");
            }
            lock.release();
            O.release();

        }

    }










}

