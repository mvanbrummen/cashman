package au.com.suncorp.cashman.controller;

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
        if (withdrawAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        Map<Money, Integer> toWithdraw = calculateWithdraw(withdrawAmount);
        deduct(toWithdraw);
    }

    private Map<Money, Integer> calculateWithdraw(BigDecimal amount) throws InsufficientFundsException,
            CurrencyCombinationException {
        Map<Money, Integer> toWithdraw = null;
        List<Money> denominations = new ArrayList<>();
        count.entrySet().forEach(entry -> {
            if (entry.getValue() > 0) {
                denominations.add(entry.getKey());
            }
        });

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
        if (getTotal(toWithdraw).compareTo(amount) != 0)
            throw new CurrencyCombinationException("Cannot make amount with available denominations");
        return toWithdraw;
    }

    private Map<Money, Integer> calculateDenominations(int position, List<Money> denominations, BigDecimal amount) {
        Map<Money, Integer> toWithdraw = new HashMap<>();
        for (int variation = 0; variation < getTotalCount(); variation++) {
            toWithdraw.clear();
            double amountTmp = amount.doubleValue();
            int variationTmp = variation;
            for (int i = position; i < denominations.size(); i++) {
                double value = denominations.get(i).getValue().doubleValue();
                if (value <= amountTmp) {
                    double count = (amountTmp / value) - variationTmp;
                    int c = (int) Math.floor(count);
                    toWithdraw.put(denominations.get(i), c);
                    // holy precision Batman!
                    amountTmp = BigDecimal.valueOf(amountTmp).subtract(BigDecimal.valueOf(c).multiply(BigDecimal.valueOf(value))).doubleValue();
                    variationTmp = 0;
                    if (getTotal(toWithdraw).compareTo(amount) == 0) {
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
        count.keySet().forEach(this::report);
    }

    private BigDecimal getTotal(Map<Money, Integer> count) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Money, Integer> entry : count.entrySet()) {
            sum = sum.add(entry.getKey().getValue().multiply(new BigDecimal(entry.getValue().intValue())));
        }
        return sum;
    }

    public BigDecimal getTotal() {
        return getTotal(count);
    }

    public boolean isInitialised() {
        return !count.isEmpty();
    }

}
