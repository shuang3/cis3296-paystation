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
public class LinearRateStrategy implements RateStrategy {

    //5 cents = 2 minutes
    //25 cents = 10 minutes
    //25 & 10 cents = 14 minutes
    @Override
    public int calculateTime(int insertedSoFar) {
        System.out.println("Current RateStrategy: Linear");
        return (insertedSoFar / 5) * 2;
    }
}
