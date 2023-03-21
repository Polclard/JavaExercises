package BinarySearchTreeImplementation;
import java.util.Scanner;

public class BinarySearchTreeImplementation {

    static void inorderSuccessor(BNode<Integer> node)
    {
        if(node != null)
        {
            inorderSuccessor(node.left);
            System.out.println(node.info);
            inorderSuccessor(node.right);
        }
    }

    public static void main(String[] args) {

         BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

        Scanner newScanner = new Scanner(System.in);
        int N = Integer.parseInt(newScanner.nextLine());

        int min = 10000000;
        for(int i = 0; i < N; i++) {

            int value = Integer.parseInt(newScanner.nextLine());
            bst.insert(value);
        }

        inorderSuccessor(bst.getRoot());

    }


}
