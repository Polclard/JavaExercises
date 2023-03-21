package Zadaca3;
import RequiredElements.OBHT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

class Driver{
    private String name;
    private String surname;
    private String licensePlate;

    public Driver(String name, String surname, String licensePlate) {
        this.name = name;
        this.surname = surname;
        this.licensePlate = licensePlate;
    }

    public String getName() {
        return name;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + licensePlate;
    }
}

class Report{
    private String licensePlate;
    private int speed;
    private SimpleDateFormat time;

    public Report(String licensePlate, int speed, SimpleDateFormat time) {
        this.licensePlate = licensePlate;
        this.speed = speed;
        this.time = time;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getSpeed() {
        return speed;
    }

    public SimpleDateFormat getTime() {
        return time;
    }

    @Override
    public String toString() {
        return licensePlate + " " + speed + " " + time.toPattern();
    }
}

public class Semafor {

    static public int hash(String givenString)
    {
        char [] charArray = givenString.toCharArray();
        return
                (int)charArray[2] +
                (int)charArray[3] +
                (int)charArray[4] +
                (int)charArray[5] +
                (int)charArray[6] +
                (int)charArray[7];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());

        OBHT<Integer, Driver> theDrivers = new OBHT<Integer, Driver>(N+5);

        for(int i = 0; i < N; i++)
        {
            String line = sc.nextLine();
            String [] splittedLine = line.split(" ");
            String licensePlate = splittedLine[0];
            String name = splittedLine[1];
            String surname = splittedLine[2];

            Driver newDriver = new Driver(name, surname, licensePlate);
            theDrivers.insert(hash(licensePlate), newDriver);

        }

        int speedLimit = Integer.parseInt(sc.nextLine());
        String dailyReport = sc.nextLine();
        String [] splittedDailyReport = dailyReport.split(" ");

        ArrayList<Report> allReports = new ArrayList<Report>();

        for(int i = 0; i < splittedDailyReport.length; i+=3)
        {
            String licencePlate = splittedDailyReport[i];
            int speed = Integer.parseInt(splittedDailyReport[i+1]);
            String time = splittedDailyReport[i+2];
            Report report  = new Report(licencePlate, speed,  new SimpleDateFormat(time));
            allReports.add(report);
        }

        ArrayList<Report> overSpeed = new ArrayList<Report>();

        for(int i = 0; i < allReports.size(); i++)
        {
            if(allReports.get(i).getSpeed() > speedLimit)
            {
                overSpeed.add(allReports.get(i));
            }
        }

        for(int j = 0; j < overSpeed.size(); j++)
        {
            int i = 0;
           while(i < overSpeed.size()-1)
            {
                if(overSpeed.get(i).getTime().toLocalizedPattern().compareTo(overSpeed.get(i+1).getTime().toPattern()) > 0)
                {
                    Report temp = overSpeed.get(i);
                    overSpeed.set(i, overSpeed.get(i+1));
                    overSpeed.set(i+1, temp);
                }
                i++;
            }
        }

        ArrayList<String> nameAndSurnames = new ArrayList<String>();

        for(int i = 0; i < overSpeed.size(); i++)
        {
            Driver drv = theDrivers.getBuckets()[theDrivers.search(hash(overSpeed.get(i).getLicensePlate()))].getValue();
            nameAndSurnames.add(drv.getName() + " " + drv.getSurname());
            System.out.println((drv.getName() + " " + drv.getSurname()));
        }


//        System.out.println(Arrays.toString(allReports.toArray()).toString());
//        System.out.println(Arrays.toString(overSpeed.toArray()).toString());
//        System.out.println(Arrays.toString(overSpeed.toArray()).toString());
//        System.out.println(theDrivers.toString());

    }
}
