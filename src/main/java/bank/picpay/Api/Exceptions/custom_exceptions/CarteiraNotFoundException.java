package bank.picpay.Api.Exceptions.custom_exceptions;

public class CarteiraNotFoundException extends RuntimeException {
    public CarteiraNotFoundException(String message) {
        super(message);
    }
}
