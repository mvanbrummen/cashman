package au.com.suncorp.cashman.controller;

import au.com.suncorp.cashman.enumeration.Note;
import au.com.suncorp.cashman.interfaces.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FundsController {

    private Map<Money, Integer> count;

    public FundsController() {
        this.count = new HashMap<>();
    }

    public FundsController(Map<Money, Integer> count) {
        this.count = count;
    }

    public void add(Money denomination, int amount) {
        if (count.containsKey(denomination)) {
            Integer total = count.get(denomination) + amount;
            count.put(denomination, total);
        } else {
            count.put(denomination, amount);
        }
    }

    public BigDecimal withdraw(BigDecimal amount) throws IllegalStateException, IllegalArgumentException {
        if (!isInitialised()) {
            throw new IllegalStateException("Must be initialised with money before a withdraw can occur");
        }
        if (amount.intValue() < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }


        return new BigDecimal(2);
    }

    public int getCount(Money denomination) {
        return count.containsKey(denomination) ? count.get(denomination) : 0;
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
