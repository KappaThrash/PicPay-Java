package bank.picpay.Api.Exceptions.custom_exceptions;

import bank.picpay.Api.Exceptions.custom_exceptions.Template.ExcecaoBase;
import org.springframework.http.HttpStatus;

public class CarteiraNotFoundException extends ExcecaoBase {
    public CarteiraNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
