package au.com.suncorp.cashman.enumeration;

import au.com.suncorp.cashman.interfaces.Money;

import java.math.BigDecimal;
import java.util.Currency;

import static au.com.suncorp.cashman.util.Constants.AUD;

public enum Note implements Money {
    TWENTY(AUD, 20),
    FIFTY(AUD, 50);

    private Currency currency;
    private BigDecimal value;

    Note(String currencyCode, int value) {
        this.currency = Currency.getInstance(currencyCode);
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", getCurrency().getCurrencyCode(), getValue());
    }

}
