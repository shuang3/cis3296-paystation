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
public class ProgressiveRateStrategy implements RateStrategy {

    // 1hr or $1.50 = 5 cents gives 2 minutes, 25 cents gives 10 minutes
    // 1-2hrs or $1.50-$3.50 = 5 cents gives 1.5 minutes, 25 cents gives 7.5 minutes
    // 2hr+ or $3.50+ = 5 cents gives 1 minute, 25 cents gives 5 minutes
    @Override
    public int calculateTime(int insertedSoFar) {
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
    }
}
