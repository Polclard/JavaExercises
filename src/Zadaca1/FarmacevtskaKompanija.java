package Zadaca1;
import RequiredElements.CBHT;
import java.util.ArrayList;
import java.util.Scanner;


class Medicine {
    private String name;
    private float price;
    private String use;

    public Medicine(String name, float price, String use) {
        this.name = name;
        this.price = price;
        this.use = use;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getUse() {
        return use;
    }

    @Override
    public String toString() {
        return "Name: "+ name + " Price: " + price + " Use: " + use + ", ";
    }
}

class ListOfMedicine{
    ArrayList<Medicine> arrayOfMedicine;

    public ListOfMedicine(ArrayList<Medicine> arrayOfMedicine) {
        this.arrayOfMedicine = arrayOfMedicine;
    }

    public ArrayList<Medicine> getArrayOfMedicine() {
        return arrayOfMedicine;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arrayOfMedicine.size(); i++)
        {
            sb.append(arrayOfMedicine.get(i).toString());
        }
        return sb.toString();
    }
}

/*
    /Во магацинот на една фармацевтска компанија се чуваат најразлични видови лекови.
     За секој лек потребно е да се чуваат податоци за името на лекот,
     цената во денари и намената на лекот. За поефикасен пристап до
     податоците за лековите, фармацевтската компанија одлучила податоците
     да ги чува во хеш табела (CBHT) каде се сместуваат соодветните податоци.

     Хеш табелата е достапна до крајните клиенти и истите може да пребаруваат низ внесените
     податоци. Бидејќи на пазарот постојат повеќе лекови кои таргетираат иста болест,
     најчесто клиентите го бараат оној лек кој има најниска цена. Па вашата задача
     е со користење на хеш табелата, за дадена намена (болест), да го испечатите
     лекот кој има најниска цена на пазарот. Доколку за бараната намена во магацинот
     нема лек се печати "Nema lek za baranata namena vo magacin.".

        Влез:

        Најпрво е даден бројот на лекови - N, а потоа секој лек е даден во нов ред во форматот:

        “Име на лек” “Намена” “Цена во денари”

        На крај е дадена намената за која треба да се пронајде лекот со најниска цена.

        Излез:

        Името на лекот со најмала цена.



        Пример:

        Влез:

        5

        Analgin Glavobolka 80

        Daleron Glavobolka 90

        Lineks Bolki_vo_stomak 150

        Spazmeks Bolki_vo_stomak 150

        Loratadin Alergija 150

        Glavobolka

        Излез:

        Analgin/

 */
public class FarmacevtskaKompanija {

    static public int hash(String useOfMedicine){
        char [] arr = useOfMedicine.toCharArray();
        return (int)arr[0] +  (int)arr[1] +  (int)arr[2];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());

        CBHT<Integer, ListOfMedicine> hashTable = new CBHT<>(N);

        for(int i = 0; i < N; i++)
        {
            String line = sc.nextLine();
            String [] splittedLine = line.split(" ");
            String medicineName = splittedLine[0];
            String useOfMedicine = splittedLine[1];
            float price = Float.parseFloat(splittedLine[2]);

            if(hashTable.search(hash(useOfMedicine)) != null)
            {
                Medicine newMedicine = new Medicine(medicineName, price, useOfMedicine);
                hashTable.search(hash(useOfMedicine)).getElement().getValue().getArrayOfMedicine().add(newMedicine);
            }
            else{
                Medicine newMedicine = new Medicine(medicineName, price, useOfMedicine);
                ArrayList<Medicine> newArr = new ArrayList<>();
                newArr.add(newMedicine);
                ListOfMedicine newList = new ListOfMedicine(newArr);
                hashTable.insert(hash(useOfMedicine), newList);
            }
        }

        String useOfMedicineInput = sc.nextLine();

        if(hashTable.search(hash(useOfMedicineInput)) == null)
        {
            System.out.println("Nema lek za baranata namena vo magacin");
        }
        else
        {
            ListOfMedicine returnedListOfMedicine = hashTable.search(hash(useOfMedicineInput)).getElement().getValue();
            ArrayList<Medicine> returnedListOfMedicineArrayList = returnedListOfMedicine.getArrayOfMedicine();

            Medicine minPriceMedicine = returnedListOfMedicineArrayList.get(0);
            for(int i = 0; i < returnedListOfMedicineArrayList.size(); i++)
            {
                if(returnedListOfMedicineArrayList.get(i).getPrice() < minPriceMedicine.getPrice())
                {
                    minPriceMedicine = returnedListOfMedicineArrayList.get(i);
                }
            }

            System.out.println(hashTable.toString());

            System.out.println(minPriceMedicine.getName());
        }



    }
}
