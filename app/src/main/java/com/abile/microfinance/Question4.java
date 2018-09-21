package com.abile.microfinance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Question4 {

    public static void main(String[] args) { // begin the main method
       /* Scanner input=new Scanner (System.in); //create a new Scanner object to use
        int counter=0, number=0, largest=0; // initialize variables
        for (counter=0; counter<10;counter++){ // loop ten times from 0 to 9
            System.out.printf("Enter Number [%d]: ", counter+1);
            number=input.nextInt(); // store next integer in number
            largest=largest>number?largest:number; // check if new number is larger, if so assign it to larger
        }
        System.out.printf("Largest = %d", largest); // display the largest value*/


       /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        String monthStart = dateFormat.format(c.getTime());

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -1); // to get previous year add -1
        String year=dateFormat.format(cal.getTime());
      System.out.println("Year: " + year);

        c = Calendar.getInstance(); // reset
        String today1 = dateFormat.format(c.getTime());

        System.out.println(monthStart + " to " + today1);*/

        //EMI = [P x R x (1+R)^N]/[(1+R)^N-1],
        int EMI=0;
        double P=10000,r=10,N=10;
        double R = (r / 12) / 100;
        double e = (P * R * (Math.pow((1 + R), N)) / ((Math.pow((1 + R), N)) - 1));
        double totalInt = Math.round((e * N) - P);
        double totalAmt = Math.round((e * N));

        System.out.println(" Total Interest -> " + totalInt);
        System.out.println(" Total Amount -> " + totalAmt);


    }
}
