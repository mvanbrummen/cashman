package au.com.suncorp.cashman.enumeration;

import java.math.BigDecimal;

public enum Denomination {
    TWENTY(new BigDecimal(20)),
    ONE_HUNDRED(new BigDecimal(100));

    private BigDecimal value;

    Denomination(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

}
