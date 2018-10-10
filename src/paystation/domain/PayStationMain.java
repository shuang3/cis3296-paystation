/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Annie
 */
public class PayStationMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PayStationImpl ps = new PayStationImpl(new LinearRateStrategy());

        String menu = "Welcome to PayStation.\n"
                + "Main menu:\n"
                + "\t1) Deposit Coins\n"
                + "\t2) Display\n"
                + "\t3) Buy Ticket\n"
                + "\t4) Cancel\n"
                + "\t5) Change Rate Strategy\n"
                + "\t6) Quit";

        Scanner kb = new Scanner(System.in);
        String choices;

        do{
            System.out.println(menu);
            System.out.print("Please select a choice: ");
            choices = kb.next();

            switch (choices) {
                case "1":
                    try {
                        System.out.print("How much do you want to deposit? ");
                        int deposit = kb.nextInt();
                        ps.addPayment(deposit);
                        System.out.print("Do you want to deposit more? Press 1 for yes, 2 for no: ");
                        int next = kb.nextInt();
                        while (next > 0 && next != 2) {
                            System.out.print("How much do you want to deposit? ");
                            int deposit2 = kb.nextInt();
                            ps.addPayment(deposit2);
                            System.out.print("Do you want to deposit more? Press 1 for yes, 2 for no: ");
                            next = kb.nextInt();
                           
                        }
                    } catch (IllegalCoinException e) {
                        System.out.println("Invalid coin type. "
                                + "Please enter 5, 10, or 25 cents.");
                    }
                    break;
                case "2":
                    System.out.println("Time bought so far: " + ps.readDisplay() + " minutes");
                    break;
                case "3":
                    Receipt receipt = ps.buy();
                    System.out.println("Thank you for your purchase. Here is your receipt: " + receipt.value() + " minutes purchased.");
                    break;
                case "4":
                    System.out.println("Canceling the current transaction... ");
                    Map map = ps.cancel();
                    int quarters = 0;
                    int dimes = 0;
                    int nickels = 0;
                    if (map.get(25) != null) {
                        quarters = (int) map.get(25);
                    } else {
                        map.put(25, 0);
                    }
                    if (map.get(10) != null) {
                        dimes = (int) map.get(10);
                    } else {
                        map.put(10, 0);
                    }
                    if (map.get(5) != null) {
                        nickels = (int) map.get(5);
                    } else {
                        map.put(5, 0);
                    }
                    System.out.printf("Here's the returned coins: %d Quarters | %d Dimes | %d Nickels\n",
                            quarters, dimes, nickels);
                    break;
                case "5":
                    System.out.println("Rate Strategies:");
                    System.out.println("\t1) Linear Rate Strategy");
                    System.out.println("\t2) Progressive Rate Strategy");
                    System.out.println("\t3) Alternating Rate Strategy");
                    System.out.print("Please choose a Rate Strategy: ");
                    int strategy = kb.nextInt();

                    while (strategy > 3 || strategy < 1) {
                        System.out.print("Please enter a valid Rate Strategy. Rate Strategy: ");
                        strategy = kb.nextInt();
                    }
                    switch (strategy) {
                        case 1:
                            System.out.println("You've choosen Linear Rate");
                            int insert = ps.getInsertedSoFar();
                            ps = new PayStationImpl(new LinearRateStrategy());
                            ps.setInsertedSoFar(insert);
                            break;
                        case 2:
                            System.out.println("You've choosen Progressive Rate");
                            insert = ps.getInsertedSoFar();
                            ps = new PayStationImpl(new ProgressiveRateStrategy());
                            ps.setInsertedSoFar(insert);
                            System.out.println("Inserted so far: "+ps.getInsertedSoFar()+" cents");
                            break;
                        case 3:
                            System.out.println("You've choosen Alternating Rate");
                            insert = ps.getInsertedSoFar();
                            ps = new PayStationImpl(new AlternatingRateStrategy());
                            ps.setInsertedSoFar(insert);
                            break;
                    }
                    break;
                case "6":
                    System.out.println("Thank you for using the PayStation, see you next time~");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    break;
            }
            System.out.println("");
        }while (!choices.equals("6")); 
    }
}
