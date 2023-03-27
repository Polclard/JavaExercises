package ExerciseClass;


import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



class My_Thread implements Runnable
{
    public String name;
    public String surname;

    static int incrementor = 1;
    My_Thread(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
    }

    public synchronized int increment()
    {
        synchronized (this)
        {
            return incrementor++;
        }
    }

    @Override
    public void run() {
        System.out.println("Name: " + this.name  + " Surname: " + this.surname + " incrementor: " + increment());
    }
}

class Person{
    String name;
    float moneyAmount;

    Person(String name, float moneyAmount)
    {
        this.name = name;
        this.moneyAmount = moneyAmount;
    }

    public String getName() {
        return name;
    }

    public float getMoneyAmount() {
        return moneyAmount;
    }

    @Override
    public String toString() {
        return name + " with " + moneyAmount + "$";
    }
}

public class Exercise {
    public static void main(String[] args) {

        Runnable thread1 = new My_Thread("Alen1", "Jangelov1");
        Runnable thread2 = new My_Thread("Alen2", "Jangelov2");
        Runnable thread3 = new My_Thread("Alen3", "Jangelov3");
        Runnable thread4 = new My_Thread("Alen4", "Jangelov4");


        Thread t1 = new Thread(thread1);
        Thread t2 = new Thread(thread2);
        Thread t3 = new Thread(thread3);
        Thread t4 = new Thread(thread4);

        Lock lock = new ReentrantLock(true);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        List<Person> listOfPeople = new ArrayList<Person>();
//
//        Person person1 = new Person("Alen", 50000f);// 50.000
//        Person person2 = new Person("Angela", 100000f);//100.000
//        Person person3 = new Person("Jovana", 1000000f);//1.000.000
//        Person person4 = new Person("Darija", 9000f);//9000
//
//        listOfPeople.add(person1);
//        listOfPeople.add(person2);
//        listOfPeople.add(person3);
//        listOfPeople.add(person4);
//
//        List<Person> over7K = listOfPeople.stream().filter(person -> person.moneyAmount > 15000f && person.moneyAmount < 100001).toList();
//
//        System.out.println(Arrays.toString(over7K.toArray()));


    }
}
