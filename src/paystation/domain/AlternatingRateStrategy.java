/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Calendar;

/**
 *
 * @author Annie
 */
public class AlternatingRateStrategy implements RateStrategy{
    
    CalendarBasedStrategy strat = new CalendarBasedStrategy();
    
    /*public AlternatingRateStrategy(){
        strat = new CalendarBasedStrategy();
    }*/
    
    @Override
    public int calculateTime(int insertedSoFar){
        System.out.println("RateStrategy: Alternating");
        /*System.out.println("Hardwiring weekend");
        strat.decision_cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        (testing purposes- comparing alt strat to progressive strat)
        */
        if (strat.isWeekend()){
             int time = 0;
        //1hr
        if (insertedSoFar <= 150) {
            time = (insertedSoFar / 5) * 2;
        } //1-2hrs
        else if (insertedSoFar <= 350) {
            insertedSoFar -= 150;
            time = 60 + (insertedSoFar / 10) * 3;
        } //2hrs+
        else {
            insertedSoFar -= 350;
            time = 120 + (insertedSoFar / 5);
        }
        return time;
        }else{
        return (insertedSoFar / 5) * 2;
        }
    }
}
