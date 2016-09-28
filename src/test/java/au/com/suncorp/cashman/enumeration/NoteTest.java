package au.com.suncorp.cashman.enumeration;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static au.com.suncorp.cashman.util.Constants.AUD;

public class NoteTest {

    @Test
    public void whenGetCurrencyThenCorrectCurrency() {
        Currency expectedCurrency = Currency.getInstance(AUD);
        Assert.assertEquals(expectedCurrency, Note.TWENTY.getCurrency());
        Assert.assertEquals(expectedCurrency, Note.FIFTY.getCurrency());
    }

    @Test
    public void whenGetValueThenCorrectValue() {
        Assert.assertEquals(new BigDecimal(20), Note.TWENTY.getValue());
        Assert.assertEquals(new BigDecimal(50), Note.FIFTY.getValue());
    }

}
