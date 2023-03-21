package Zadaca2;
import RequiredElements.DLL;
import RequiredElements.DLLNode;
import RequiredElements.SLLNode;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DveListi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int M = Integer.parseInt(line.split(" ")[0]);
        int N = Integer.parseInt(line.split(" ")[1]);

        DLL<Integer> firstList = new DLL<Integer>();
        DLL<Integer> secondList = new DLL<Integer>();

        for(int i = 0; i < M; i++)
        {
            firstList.insertLast(sc.nextInt());
        }
        for(int i = 0; i < N; i++)
        {
            secondList.insertLast(sc.nextInt());
        }

        DLLNode<Integer> firstPtr = firstList.getLast();
        DLLNode<Integer> secondPtr = secondList.getFirst();

        DLL<Integer> finalList = new DLL<Integer>();

        while(firstPtr != null || secondPtr != null) {

            if(firstPtr == null || secondPtr == null)
                break;
            if( firstPtr.getElement() >= secondPtr.getElement())
            {
                finalList.insertLast(firstPtr.getElement());
                firstPtr = firstPtr.getPred();
            }
            else if(secondPtr.getElement() > firstPtr.getElement())
            {
                finalList.insertLast(secondPtr.getElement());
                secondPtr = secondPtr.getSucc();
            }
        }
        while(firstPtr != null)
        {
            finalList.insertLast(firstPtr.getElement());
            firstPtr = firstPtr.getPred();
        }
        while(secondPtr != null)
        {
            finalList.insertLast(secondPtr.getElement());
            secondPtr = secondPtr.getSucc();
        }


        System.out.println(finalList.toString());
        System.out.println(finalList.toStringR());


        /*
5 5
1 3 4 6 7
9 8 5 2 1
         */
    }
}
