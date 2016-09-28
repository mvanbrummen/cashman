package au.com.suncorp.cashman.controller;

import au.com.suncorp.cashman.enumeration.Note;
import au.com.suncorp.cashman.exceptions.CurrencyCombinationException;
import au.com.suncorp.cashman.exceptions.InsufficientFundsException;
import au.com.suncorp.cashman.interfaces.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

    public void withdraw(BigDecimal withdrawAmount) throws IllegalStateException, IllegalArgumentException,
            CurrencyCombinationException, InsufficientFundsException  {
        if (!isInitialised()) {
            throw new IllegalStateException("Must be initialised with money before a withdraw can occur");
        }
        int amount = withdrawAmount.intValue();
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        Map<Money, Integer> toWithdraw = calculateWithdraw(amount);
        deduct(toWithdraw);
    }

    private Map<Money, Integer> calculateWithdraw(int amount) throws InsufficientFundsException,
            CurrencyCombinationException {
        Map<Money, Integer> toWithdraw = null;
        List<Money> denominations = new ArrayList<>(count.keySet());

        // reverse sort denominations
        denominations.sort(Comparator.comparing(Money::getValue).reversed());

        // greedy algorithm to find denominations to make amount - try biggest notes first
        for (int i = 0; i < denominations.size(); i++) {
            toWithdraw = calculateDenominations(i, denominations, amount);
            if (fundsAvailable(toWithdraw)) {
                break;
            }
        }
        if (!fundsAvailable(toWithdraw))
            throw new InsufficientFundsException("Insufficient funds to make withdrawal");
        if (getTotal(toWithdraw) != amount)
            throw new CurrencyCombinationException("Cannot make amount with available denominations");
        return toWithdraw;
    }

    private Map<Money, Integer> calculateDenominations(int position, List<Money> denominations, int amount) {
        Map<Money, Integer> toWithdraw = new HashMap<>();
        for (int variation = 0; variation < getTotalCount(); variation++) {
            toWithdraw.clear();
            int amountTmp = amount;
            int variationTmp = variation;
            for (int i = position; i < denominations.size(); i++) {
                int value = denominations.get(i).getValue().intValue();
                if (value <= amountTmp) {
                    int count = (amountTmp / value) - variationTmp;
                    toWithdraw.put(denominations.get(i), count);
                    amountTmp -= count * value;
                    variationTmp = 0;
                    if (getTotal(toWithdraw) == amount) {
                        return toWithdraw;
                    }
                }
            }
        }
        return toWithdraw;
    }

    private boolean fundsAvailable(Map<Money, Integer> toWithdraw) {
        for (Map.Entry<Money, Integer> entry : toWithdraw.entrySet()) {
            if (!count.containsKey(entry.getKey()))
                return false;
            if (count.get(entry.getKey()) < entry.getValue())
                return false;
        }
        return true;
    }

    private void deduct(Map<Money, Integer> toWithdraw) {
        toWithdraw.entrySet().forEach(entry -> {
            Integer deduction = count.get(entry.getKey()) - entry.getValue();
            count.put(entry.getKey(), deduction);
        });
    }

    public int getCount(Money denomination) {
        return count.containsKey(denomination) ? count.get(denomination) : 0;
    }

    public int getTotalCount() {
        int sum = 0;
        for (Map.Entry<Money, Integer> entry : count.entrySet()) {
            sum += getCount(entry.getKey());
        }
        return sum;
    }

    public void report(Money denomination) {
        System.out.printf("%s : %d\n", denomination.toString(), getCount(denomination));
    }

    public void reportAll() {
        for (Money denomination : Note.values()) {
            report(denomination);
        }
    }

    private int getTotal(Map<Money, Integer> count) {
        int sum = 0;
        for (Map.Entry<Money, Integer> entry : count.entrySet()) {
            sum += entry.getKey().getValue().intValue() * entry.getValue().intValue();
        }
        return sum;
    }

    public int getTotal() {
        return getTotal(count);
    }

    public boolean isInitialised() {
        return !count.isEmpty();
    }

}
