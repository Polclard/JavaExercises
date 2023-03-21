package Apteka;
import RequiredElements.SLLNode;
import RequiredElements.CBHT;
import RequiredElements.MapEntry;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;



class Lek{
    String name;
    int onPositiveList;
    int price;
    int quantity;

    public Lek(String name, int onPositiveList, int price, int quantity) {
        this.name = name;
        this.onPositiveList = onPositiveList;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getOnPositiveList() {
        return onPositiveList;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnPositiveList(int onPositiveList) {
        this.onPositiveList = onPositiveList;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
//        return name + "\n" + (onPositiveList==1 ? "POS" : "NEG") + "\n" + price + "\n" + quantity;
        return name + " " + (onPositiveList==1 ? "POS" : "NEG") + " " + price + " " + quantity;
    }

    public int hashFunction()
    {
        String key = name;
        char [] chars = key.toUpperCase().toCharArray();
        return (29*(29*((int)chars[0])+(int)(chars[1])) + (int)(chars[2]))% 102780;
    }

}


class ListOfMedicine{
    private ArrayList<Lek> thelistOfMedicine;

    public ListOfMedicine(ArrayList<Lek> thelistOfMedicine)
    {
        this.thelistOfMedicine  = thelistOfMedicine;
    }

    @Override
    public String toString() {
        return thelistOfMedicine.toString();
    }

    public int length()
    {
        return thelistOfMedicine.size();
    }

    public ArrayList<Lek> getThelistOfMedicine() {
        return thelistOfMedicine;
    }
}

public class Apteka {

    static int hashFunction(String key)
    {
        char [] chars = key.toUpperCase().toCharArray();
        return (29*(29*((int)chars[0])+(int)(chars[1])) + (int)(chars[2]))% 102780;
    }


    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();

        CBHT<Integer, ListOfMedicine> hashTable = new CBHT<>(n);

        for(int i = 0; i < n; i++) {
            String line = sc.nextLine();

            String[] splittedLine = line.split(" ");

            String name = splittedLine[0].toUpperCase();
            int onPositiveList = Integer.parseInt(splittedLine[1]);
            int price = Integer.parseInt(splittedLine[2]);
            int quantity = Integer.parseInt(splittedLine[3]);

            Lek novLek = new Lek(name, onPositiveList, price, quantity);


            int flag = 0;

            if (hashTable.search(hashFunction(name)) == null) {
                ArrayList<Lek> noviLekove = new ArrayList<Lek>();
                noviLekove.add(novLek);
                ListOfMedicine newMedicines = new ListOfMedicine(noviLekove);
                hashTable.insert(hashFunction(name), newMedicines);
            } else {
                hashTable.search(hashFunction(name)).getElement().getValue().getThelistOfMedicine().add(novLek);
            }
        }
        System.out.println("Tabela:\n " + hashTable.toString());

        while(true)
        {
            String medicine = sc.nextLine().toUpperCase();
            if(medicine.compareTo("KRAJ") == 0)
                break;

            int howMuch = Integer.parseInt(sc.nextLine());

            int flag = 0;

            if(hashTable.search(hashFunction(medicine)) == null)
            {
                System.out.println("Nema takov lek");
            }
            else
            {
                flag = 0;
                ArrayList<Lek> theArray= hashTable.search(hashFunction(medicine)).getElement().getValue().getThelistOfMedicine();
                for(int i = 0; i < theArray.size(); i++)
                {
                    if(theArray.get(i).name.compareTo(medicine) == 0)
                    {
                        if(howMuch <= theArray.get(i).quantity)
                        {
                            flag = 1;
                            System.out.println(theArray.get(i));
                            theArray.get(i).setQuantity(theArray.get(i).quantity - howMuch);
                            System.out.println("Napravena naracka");
                            break;
                        }
                        else
                        {
                            System.out.println(theArray.get(i));
                            System.out.println("Nema dovolno lekovi");
                            flag = 1;
                            break;
                        }
                    }

                }
                if(flag == 0)
                {
                    System.out.println("Nema takov lek");
                }
            }

        }

    }
}
