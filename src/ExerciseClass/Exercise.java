package ExerciseClass;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<Person> listOfPeople = new ArrayList<Person>();

        Person person1 = new Person("Alen", 50000f);// 50.000
        Person person2 = new Person("Angela", 100000f);//100.000
        Person person3 = new Person("Jovana", 1000000f);//1.000.000
        Person person4 = new Person("Darija", 9000f);//9000

        listOfPeople.add(person1);
        listOfPeople.add(person2);
        listOfPeople.add(person3);
        listOfPeople.add(person4);

        List<Person> over7K = listOfPeople.stream().filter(person -> person.moneyAmount > 15000f).toList();

        System.out.println(Arrays.toString(over7K.toArray()));

    }
}
