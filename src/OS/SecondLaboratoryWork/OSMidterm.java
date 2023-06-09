package OS.SecondLaboratoryWork;

import java.util.Random;
import java.util.concurrent.Semaphore;

class OSMidterm {
    //Initialize the semaphores you need
    //
    private static Semaphore semaphoreConcatenation = new Semaphore(1); //The semaphoreConcatenation semaphore is used to ensure that only one thread at a time can access the critical section where the concatenation of the rows is performed (staticResource).
    private static Semaphore semaphoreRow = new Semaphore(0); //The semaphoreRow semaphore is used to keep track of the number of rows that have been processed so far. It starts with 0 permits, meaning that no rows have been processed yet.

    private static int whichRowCounter = 0;
    public static void main(String[] args) throws InterruptedException {

        //STARTING CODE, DON'T MAKE CHANGES
        //-----------------------------------------------------------------------------------------
        String final_text = "Bravo!!! Ja resi zadacata :)";
        int m = 10, n = 100;
        Object[][] data = new Object[m][n];
        Random rand = new Random();
        int k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int random = rand.nextInt(100);
                if (random % 2 == 0 & k < final_text.length()) {
                    data[i][j] = final_text.charAt(k);
                    k++;
                } else {
                    data[i][j] = rand.nextInt(100);
                }
            }
        }

        DataMatrix matrix = new DataMatrix(m, n, data);
        StatisticsResource statisticsResource = new StatisticsResource();
        //-----------------------------------------------------------------------------------------

        //region Test Code
            //ONLY TESTING CODE, SO YOU CAN TAKE A LOOK OF THE OUTPUT YOU NEED TO GET AT THE END
            //YOU CAN COMMENT OR DELETE IT AFTER YOU WRITE THE CODE USING THREADS
            //-----------------------------------------------------------------------------------------
                //Concatenation concatenation = new Concatenation(matrix, statisticsResource);
                //concatenation.concatenate();
                //statisticsResource.printString();
            //-----------------------------------------------------------------------------------------
        //endregion


        // Run the threads from the Concatenation class
        Concatenation[] threads = new Concatenation[m];
        for (int i = 0; i < m; i++) {
            threads[i] = new Concatenation(matrix, statisticsResource);
            threads[i].start();
        }

        // Wait 10 seconds for all threads to finish
        Thread.sleep(10000);

        // Print the string you get, call function printString()
        statisticsResource.printString();

        // Check if some thread is still alive, if so kill it and print "Possible deadlock"
        for (int i = 0; i < m; i++) {
            if (threads[i].isAlive()) {
                threads[i].interrupt();
                System.out.println("Possible deadlock");
            }
        }

    }






    //Make the Concatenation Class  a Thread Class
    static class Concatenation extends Thread{

        private DataMatrix matrix;
        private StatisticsResource statisticsResource;

        public Concatenation(DataMatrix matrix, StatisticsResource statisticsResource) {
            this.matrix = matrix;
            this.statisticsResource = statisticsResource;
        }


        public void concatenate_by_row(){
            try {
                semaphoreConcatenation.acquire(); //It first acquires the semaphoreConcatenation semaphore, which ensures that only one thread at a time is accessing the matrix object.
                StringBuilder concatenatedRow = new StringBuilder(); //It then creates a new StringBuilder object to hold the concatenated row.
                Object[] row = matrix.getRow(whichRowCounter++); //It gets the next row of the matrix by calling matrix.getRow(whichRowCounter++), where whichRowCounter is a counter that keeps track of which row each thread should access.
                for (int j = 0; j < row.length; j++) { //It then iterates over the elements in the row,
                    Object element = row[j]; //Getting the element from each column in the row
                    if (element instanceof Character) { //Checking if each element is a Character.
                        concatenatedRow.append(element); //If it is, it appends it to the concatenatedRow StringBuilder.
                    }
                }
                statisticsResource.concatenateString(concatenatedRow.toString()); //It adds the concatenatedRow to the statisticsResource
                semaphoreRow.release(); //It then releases the semaphoreRow semaphore, which signals to the main thread that it can continue executing.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void execute() throws InterruptedException{
            //call the concatenate_by_row() function
            try {
                concatenate_by_row(); //It calls concatenate_by_row() to concatenate a single row of the matrix.
                semaphoreRow.acquire(); //It then acquires the semaphoreRow semaphore, which ensures that the main thread doesn't continue until the current row has been fully concatenated and added to statisticsResource.
                semaphoreConcatenation.release(); //It releases the semaphoreConcatenation semaphore, which allows the next thread to access the matrix object.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void run() {
            try{
                execute();
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }
    }
    //-------------------------------------------------------------------------
    //YOU ARE NOT CHANGING THE CODE BELOW
    static class DataMatrix {

        private int m,n;
        private Object[][] data;

        public DataMatrix(int m, int n, Object[][] data) {
            this.m = m;
            this.n = n;
            this.data = data;
        }

        public int getM() {
            return m;
        }

        public int getN() {
            return n;
        }

        public Object[][] getData() {
            return data;
        }

        public Object getEl(int i, int j) {
            return data[i][j];
        }

        public Object[] getRow(int pos) {
            return this.data[pos];
        }

        public Object[] getColumn(int pos) {
            Object[] result = new Object[m];
            for (int i=0;i<m;i++) {
                result[i]=data[i][pos];
            }
            return result;
        }

        public boolean isString(int i, int j) {
            return this.data[i][j] instanceof Character;
        }


    }

    static class StatisticsResource {

        private String concatenatedString;

        public StatisticsResource() {
            this.concatenatedString = "";
        }

        //function for String concatenation
        public void concatenateString(String new_character) {
            concatenatedString+=new_character;
        }

        //function for printing the concatenated string
        public void printString() {
            System.out.println("Here is the phrase from the matrix: " + concatenatedString);
        }

    }
}