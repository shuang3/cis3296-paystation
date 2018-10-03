/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

/**
 *
 * @author Annie
 */
public class AlternatingRateStrategy implements RateStrategy{
    
    CalendarBasedStrategy strat;
    
    public AlternatingRateStrategy(){
        strat = new CalendarBasedStrategy();
    }
    
    @Override
    public int calculateTime(int insertedSoFar){
        if (strat.isWeekend()){
             int time = 0;
        System.out.println("RateStrategy: Progressive");
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
            System.out.println("RateStrategy: Linear");
        return (insertedSoFar / 5) * 2;
        }
    }
}
