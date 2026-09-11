package bank.picpay.api.exceptions.custom_exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
