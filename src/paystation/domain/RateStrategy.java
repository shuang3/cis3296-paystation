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
public interface RateStrategy {

    //calculate the parking time
    public int calculateTime(int insertedSoFar);
}
