package cl.tenpo.challengecalcservice.exception;


public class CalculationFailedException extends RuntimeException {
    public CalculationFailedException(String message) {
        super(message);
    }
}