package bank.picpay.Api.exceptions.custom_exceptions;

public class CarteiraNotFoundException extends RuntimeException {
    public CarteiraNotFoundException(String message) {
        super(message);
    }
}
