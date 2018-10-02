/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }

    //Lab3: new tests being added
    //total of 8 new tests added
    
    @Test //call to empty returns the total amount entered
    public void emptyReturnsTotal() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.buy();
        ps.addPayment(10);
        ps.buy();
        assertEquals("Should return the total amount entered", 25, ps.empty());
    }

    @Test //canceled entry does not add to the amount returned by empty.
    public void cancelEntryNotAdded() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.buy();
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Should return total amount after cancel", 15, ps.empty());
    }

    @Test //call to empty resets the total to zero.
    public void emptyResetsTotalToZero() throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy();
        assertEquals("Should return total amount entered", 25, ps.empty());
        assertEquals("Should now reset total to 0", 0, ps.empty());
    }

    @Test //call to cancel returns a map containing one coin entered.
    public void cancelReturnsOneCoin() throws IllegalCoinException {
        ps.addPayment(5);
        Map m = ps.cancel();
        assertEquals("Should return a nickel", 1, m.get(5));
        //ps.addPayment(10);
        //m = ps.cancel();
        //assertEquals("Should return a dime", 1, m.get(10));
        //ps.addPayment(25);
        //m = ps.cancel();
        //assertEquals("Should return a quarter", 1, m.get(25));
    }

    @Test //call to cancel returns a map containing a mixture of coins entered.
    public void cancelReturnsMixCoins() throws IllegalCoinException {
        ps.addPayment(25);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(5);
        ps.addPayment(5);
        Map m = ps.cancel();
        assertEquals("Should return nickel", 4, m.get(5));
        assertEquals("Should return dime", 3, m.get(10));
        assertEquals("Should return quarter", 1, m.get(25));
    }

    @Test //call to cancel returns a map that does not contain a key for a coin not entered.
    public void cancelReturnsOnlyCoinsEntered() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);
        Map m = ps.cancel();
        assertEquals("Should return a dime", 1, m.get(10));
        assertEquals("Should return two quarters", 2, m.get(25));
        //assertEquals("Should return a empty map", true, m.isEmpty());
    }

    @Test //call to cancel clears the map.
    public void cancelClearsMap() throws IllegalCoinException {
        ps.addPayment(5);
        ps.cancel();
        Map m = ps.cancel();
        Map e = new HashMap<>();
        assertEquals("Cancel should clear map", e, m);
        //assertEquals("Cancel should clear map", 0, m.size());
    }

    @Test //call to buy clears map
    public void buyClearsMap() throws IllegalCoinException {
        ps.addPayment(5);
        ps.buy();
        Map m = ps.cancel();
        Map e = new HashMap<>();
        assertEquals("Buy should clear map", e, m);
        //assertEquals("Buy should clear map", 0, m.size());

    }
}
