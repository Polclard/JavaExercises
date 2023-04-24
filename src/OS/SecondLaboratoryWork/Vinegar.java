package OS.SecondLaboratoryWork;

import java.util.HashSet;
import java.util.concurrent.Semaphore;


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
        C = new Semaphore(2); //Two atoms of Carbon
        H = new Semaphore(4); //Four atoms of Hydrogen
        O = new Semaphore(2); //Two atoms of Oxygen


        canBond = new Semaphore(0); //At first zero
        finish = new Semaphore(0); //At first zero

        lock = new Semaphore(1); //Lock is always one for locking the shared variable totalNumOfAtoms
        totalNumOfAtoms = 0; //Shared counter
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
        for(Thread thread : threads)
        {
           if(thread.isAlive())
           {
               thread.interrupt();
               System.out.println("Possible deadlock!");
           }
        }
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

            C.acquire(); //We are acquiring permit from one of C Semaphore (atoms)
            System.out.println("C here."); //When we get the permit (atom) we say C is here
            lock.acquire(); //The we are locking (acquiring) the shared counter, preventing it from unauthorized changes
            totalNumOfAtoms++; //We are incrementing the totalNumOfAtoms which like the name says gives us the total number of atoms
            if(totalNumOfAtoms == 2) // We need two atoms from Carbon so if we get them
            {
                okay = true; //We set the okay boolean to true
                canBond.release(8); // and we release permit to every other 2+4+2
            }
            lock.release(); //We are releasing the lock as well for the totalNumOfAtoms


            // after all atoms are present, they should start with the bonding process together
            canBond.acquire();
            System.out.println("Molecule bonding.");

//            Thread.sleep(100);// this represents the bonding process

            lock.acquire();//We are locking (acquiring) the shared counter, preventing it from unauthorized changes
            totalNumOfAtoms--; //We are decrementing the totalNumOfAtoms which like the name says gives us the total number of atoms
            if(totalNumOfAtoms == 0) //If there are no more atoms that means they've all bonded successfully
            {
                finish.release(8);//We can release them, so finish.release() is called
            }
            lock.release();//We are releasing the lock as well for the totalNumOfAtoms

            finish.acquire(); //If the totalNumOfAtoms is not 0, we acquire a permit to finish for every atom
            System.out.println("C done."); //At the end all of them are bonded, finished, so we print "C done."



            /*
                This code block handles the creation
                of a molecule once all the atoms
                required for bonding are present.
            */
            finish.acquire();
            if(okay) //If okay is true
            {
                okay = false;
                totalN++;//The totalN counter is incremented to keep track of the number of molecules that have been created.
                // Only one atom should print the next line, representing that the molecule is created
                System.out.println("Molecule created."); //Only one atoms prints this because for every other atom the okay is false
            }
            lock.release();//We are releasing the lock as well for the totalNumOfAtoms
            C.release(); //We are releasing the atom because we don't longer need it
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

