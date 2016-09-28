package au.com.suncorp.cashman.controller;

import au.com.suncorp.cashman.enumeration.Note;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class FundsControllerTest {

    @Test
    public void whenAddThenMoneyAdded() {
        FundsController fundsController = new FundsController();
        fundsController.add(Note.ONE_HUNDRED, 26);
        Assert.assertEquals(26, fundsController.getCount(Note.ONE_HUNDRED));
        fundsController.add(Note.ONE_HUNDRED, 1);
        Assert.assertEquals(27, fundsController.getCount(Note.ONE_HUNDRED));
        fundsController.add(Note.TWENTY, 3);
        Assert.assertEquals(3, fundsController.getCount(Note.TWENTY));
    }

    @Test(expected = IllegalStateException.class)
    public void whenWithdrawOnNotInitiliasedThenIllegalStateException() {
        FundsController fundsController = new FundsController();
        fundsController.withdraw(new BigDecimal(100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWithdrawNegativeThenIllegalArgumentException() {
        FundsController fundsController = new FundsController();
        fundsController.add(Note.ONE_HUNDRED, 26);
        fundsController.withdraw(new BigDecimal(-100));
    }

    @Test
    public void whenGetCountThenReturnAmount() {
        FundsController fundsController = new FundsController();
        Assert.assertEquals(0, fundsController.getCount(Note.ONE_HUNDRED));
        Assert.assertEquals(0, fundsController.getCount(Note.TWENTY));
        fundsController.add(Note.ONE_HUNDRED, 1);
        fundsController.add(Note.TWENTY, 3);
        Assert.assertEquals(1, fundsController.getCount(Note.ONE_HUNDRED));
        Assert.assertEquals(3, fundsController.getCount(Note.TWENTY));
        fundsController.add(Note.ONE_HUNDRED, 150);
        fundsController.add(Note.TWENTY, 200);
        Assert.assertEquals(151, fundsController.getCount(Note.ONE_HUNDRED));
        Assert.assertEquals(203, fundsController.getCount(Note.TWENTY));
    }

    @Test
    public void whenNotInitiliasedThenReturnFalse() {
        FundsController fundsController = new FundsController();
        Assert.assertFalse(fundsController.isInitialised());
    }

    @Test
    public void whenInitiliasedThenReturnTrue() {
        FundsController fundsController = new FundsController();
        fundsController.add(Note.ONE_HUNDRED, 26);
        Assert.assertTrue(fundsController.isInitialised());
    }

}
