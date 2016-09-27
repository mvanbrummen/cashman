package au.com.suncorp.cashman.interfaces;

import java.math.BigDecimal;
import java.util.Currency;

public interface Money {

    BigDecimal getValue();

    Currency getCurrency();

}
