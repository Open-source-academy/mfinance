package com.abile.microfinance.FinanceDatabaseHandler;

public class EMCalc {

    public static void main(String[] args) {
        EMCalc eCalc = new EMCalc();
        eCalc.calcEmiAllMonths(10000, 10, 10);
    }

    public Double calcEmi(double p, double r, double n) {
        double R = (r / 7) / 100;
        double e = (p * R * (Math.pow((1 + R), n)) / ((Math.pow((1 + R), n)) - 1));

        return e;
    }

    public void calcEmiAllMonths(double p, double r, double n) {

        double R = (r / 12) / 100;
        double P = p;
        double e = calcEmi(P, r, n);
        double totalInt = Math.round((e * n) - p);
        double totalAmt = Math.round((e * n));
        System.out.println("***************************");
        System.out.println(" Principal-> " + P);
        System.out.println(" Rate of Interest-> " + r);
        System.out.println(" Number of Months-> " + n);
        System.out.println(" EMI -> " + Math.round(e));
        System.out.println(" Total Interest -> " + totalInt);
        System.out.println(" Total Amount -> " + totalAmt);
        System.out.println("***************************");
        double intPerMonth = Math.round(totalInt / n);

        for (double i = 1; i <= n; i++) {
            intPerMonth = (P * R);
            P = ((P) - ((e) - (intPerMonth)));
            System.out.println(" Month -> " + (int) i);
            System.out.println(" Interest per month -> "
                    + Math.round(intPerMonth));
            System.out.println(" Principal per month -> "
                    + Math.round((e) - intPerMonth));
            System.out.println(" Balance Principal -> " + Math.round(P));
            System.out.println("***************************");
        }
    }

}
