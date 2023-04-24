package OS;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class CountAB {

    public static int NUM_RUNS = 2;
    /**
     * Promenlivi koja treba da go sodrzat brojot na pojavuvanja na karakterite A i B.
     */
    int countA = 0;
    int countB = 0;

    /**
     * Promenliva koja treba da go sodrzi prosecniot brojot na pojavuvanja na karakterite A i B.
     */
    double average = 0.0;
    /**
     * TODO: definirajte gi potrebnite elementi za sinhronizacija
     */

    Semaphore semaforA;
    Semaphore semaforB;
    public void init() {
        semaforA=new Semaphore(1);
        semaforB=new Semaphore(1);
    }

    class CounterA extends Thread {

        public void count(char[] data) throws InterruptedException {
            for (char datum : data) {
                if (datum == 'A') {
                    semaforA.acquire();
                    countA++;
                    semaforA.release();
                }
            }

        }
        private char[] data;

        public CounterA(char[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                count(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class CounterB extends Thread {

        public void count(char[] data) throws InterruptedException {
            for (char datum : data) {
                if (datum == 'B') {
                    semaforB.acquire();
                    countB++;
                    semaforB.release();
                }
            }

        }
        private char[] data;

        public CounterB(char[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                count(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            CountAB environment = new CountAB();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {

        init();

        HashSet<Thread> threads = new HashSet<Thread>();
        Scanner s = new Scanner(System.in);
        int total=s.nextInt();

        for (int i = 0; i < NUM_RUNS; i++) {
            char[] data = new char[total];
            for (int j = 0; j < total; j++) {
                data[j] = s.next().charAt(0);
            }
            CounterA c = new CounterA(data);
            threads.add(c);
        }

        for (int i = 0; i < NUM_RUNS; i++) {
            char[] data = new char[total];
            for (int j = 0; j < total; j++) {
                data[j] = s.next().charAt(0);
            }
            CounterB c = new CounterB(data);
            threads.add(c);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        average = (countA + countB) / ((double)total * (double)NUM_RUNS * 2);

        System.out.println(countA+"-"+countB);
        System.out.println(average);

        //ANCO JA RESI SO TOA SO NE JA RESI GG
    }
}