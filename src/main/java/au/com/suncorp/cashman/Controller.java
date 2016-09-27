package au.com.suncorp.cashman;

import au.com.suncorp.cashman.enumeration.Denomination;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Map<Denomination, Integer> count;

    public Controller() {
        this.count = new HashMap<>();
    }

    public void add(Denomination denomination, int count) {
        this.count.put(denomination, count);
    }

    public void report(Denomination denomination) {
        System.out.println(count.get(denomination));
    }

    public void reportAll() {
        for (Denomination denomination : Denomination.values()) {
            report(denomination);
        }
    }

}
