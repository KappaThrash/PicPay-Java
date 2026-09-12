package bank.picpay.Api.Exceptions.custom_exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
