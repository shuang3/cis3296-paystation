/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;
import java.util.Calendar;

public class CalendarBasedStrategy {
    Calendar decision_cal;
    
    public CalendarBasedStrategy(){
        decision_cal = Calendar.getInstance();
    }
    
    public boolean isWeekend(){
        int today = this.decision_cal.get(Calendar.DAY_OF_WEEK);
        if (today != Calendar.SUNDAY && today != Calendar.SATURDAY){
            return false;
        }
        return true;
    }
    
    
}
