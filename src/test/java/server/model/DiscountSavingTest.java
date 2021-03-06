package server.model;

import exceptions.DataException;
import org.junit.Test;

import java.util.Date;

public class DiscountSavingTest {
    @Test
    public void testSavingDiscounts(){
        Date start = new Date();
        Date end = new Date();
        DiscountCode discountCode = new DiscountCode(start, end, 10, 100, 2);
        try {
            DiscountCode.saveData();
        } catch (DataException e) {
            e.printStackTrace();
        }
    }
}
