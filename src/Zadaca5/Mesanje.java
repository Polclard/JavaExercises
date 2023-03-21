package Zadaca5;
import RequiredElements.DLL;
import RequiredElements.DLLNode;

import java.util.Scanner;

public class Mesanje {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = Integer.parseInt(sc.nextLine());
        int N = Integer.parseInt(sc.nextLine());

        DLL<Integer> lista = new DLL<Integer>();

        for(int i = 0; i < M; i++)
        {
            int newNumber = sc.nextInt();
            lista.insertLast(newNumber);
        }

        DLLNode<Integer> ptr;

        System.out.println(lista);

        for(int i = 0; i < N; i++)
        {
            ptr = lista.getFirst();
            int cumulativeSum = ptr.getElement();
            while(ptr != null)
            {


            }
        }

        System.out.println(lista);

    }
}
