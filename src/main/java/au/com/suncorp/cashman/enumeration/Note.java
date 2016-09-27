package au.com.suncorp.cashman.enumeration;

import au.com.suncorp.cashman.interfaces.Money;

import java.math.BigDecimal;
import java.util.Currency;

import static au.com.suncorp.cashman.util.Constants.AUD;

public enum Note implements Money {
    TWENTY(Currency.getInstance(AUD), new BigDecimal(20)),
    ONE_HUNDRED(Currency.getInstance(AUD), new BigDecimal(100));

    private Currency currency;
    private BigDecimal value;

    Note(Currency currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

}
