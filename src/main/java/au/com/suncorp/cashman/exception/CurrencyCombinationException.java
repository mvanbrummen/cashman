package au.com.suncorp.cashman.exception;

public class CurrencyCombinationException extends Exception {

    public CurrencyCombinationException(String message) {
        super(message);
    }

    public CurrencyCombinationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
