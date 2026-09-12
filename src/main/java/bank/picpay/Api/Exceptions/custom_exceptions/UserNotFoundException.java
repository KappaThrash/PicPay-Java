package bank.picpay.Api.Exceptions.custom_exceptions;

import bank.picpay.Api.Exceptions.custom_exceptions.Template.ExcecaoBase;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ExcecaoBase {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
