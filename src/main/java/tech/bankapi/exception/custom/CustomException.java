package tech.bankapi.exception.custom;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
