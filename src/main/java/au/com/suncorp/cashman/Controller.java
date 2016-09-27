package au.com.suncorp.cashman;

import au.com.suncorp.cashman.enumeration.Note;
import au.com.suncorp.cashman.interfaces.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Map<Money, Integer> count;

    public Controller() {
        this.count = new HashMap<>();
    }

    public Controller(Map<Money, Integer> count) {
        this.count = count;
    }

    public void add(Money denomination, int count) {
        this.count.put(denomination, count);
    }

    public BigDecimal withdraw(BigDecimal amount) throws IllegalStateException, IllegalArgumentException {
        if (!isInitialised()) {
            throw new IllegalStateException("Must be initialised with money before a withdraw can occur");
        }
        if (amount.intValue() < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        return new BigDecimal(2); // TODO return money to be withdrawn
    }

    public int getCount(Money denomination) {
        return count.get(denomination);
    }

    public void report(Money denomination) {
        System.out.printf("%s %.2f : %d\n", denomination.getCurrency().getCurrencyCode(),
                denomination.getValue().doubleValue(),
                getCount(denomination));
    }

    public void reportAll() {
        for (Money denomination : Note.values()) {
            report(denomination);
        }
    }

    public boolean isInitialised() {
        return !count.isEmpty();
    }

}
