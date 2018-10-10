package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 2) Calculate parking time based on payment; 3) Know
 * earning, parking time bought; 4) Issue receipts; 5) Handle buy and cancel
 * events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {

    private int insertedSoFar;
    private int timeBought;
    private int totalMoney;

    private HashMap map = new HashMap();

    //strategy for rate calculations
    private RateStrategy rateStrategy;

    public PayStationImpl(RateStrategy rs) {
        rateStrategy = rs;
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5:
                break;
            case 10:
                break;
            case 25:
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }

        int c = coinValue;
        int n = 1;
        if (map.containsKey(c)) {
            n = (Integer) map.get(c);
            n++;
        }
        map.put(c, n);
        insertedSoFar += coinValue;
        //timeBought = insertedSoFar / 5 * 2;
       // timeBought = rateStrategy.calculateTime(insertedSoFar);
    }

    @Override
    public int readDisplay() {
        timeBought = rateStrategy.calculateTime(insertedSoFar);
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalMoney += insertedSoFar;
        reset();
        return r;
    }

    @Override
    //public void cancel() {
    public Map<Integer, Integer> cancel() {
        HashMap coinReturn = (HashMap) map.clone();
        coinReturn.putAll(map);

        /*
        Map<Integer, Integer> coinReturn = new HashMap<>();
        int quarters = 0, dimes = 0, nickels = 0;

        while (insertedSoFar >= 25) {
            quarters++;
            coinReturn.put(25, quarters);
            insertedSoFar -= 25;
        }
        while (insertedSoFar >= 10) {
            dimes++;
            coinReturn.put(10, dimes);
            insertedSoFar -= 10;
        }
        while (insertedSoFar >= 5) {
            nickels++;
            coinReturn.put(5, nickels);
            insertedSoFar -= 5;
        }
         */
        reset();
        return coinReturn;
    }

    private void reset() {
        timeBought = insertedSoFar = 0;
        map.clear();
    }

    @Override
    public int empty() {
        //int inserted = insertedSoFar;
        //return inserted;
        int total = totalMoney;
        totalMoney = 0;
        return total;
    }
    
    public int getInsertedSoFar(){
        return this.insertedSoFar;
    }
    public void setInsertedSoFar(int cents){
        this.insertedSoFar = cents;
    }
    
    public int getTotalMoney(){
        return this.totalMoney;
    }
    
    public int getTimeBought(){
        return this.timeBought;
    }
    public void setTotalMoney(int tot){
        this.totalMoney = tot;
    }
    public void setTimeBought(int time){
        this.timeBought = time;
    }
}
