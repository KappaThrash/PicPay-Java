package bank.picpay.Api.Exceptions.custom_exceptions;

import bank.picpay.Api.Exceptions.custom_exceptions.Template.ExcecaoBase;
import org.springframework.http.HttpStatus;

public class BusinessException extends ExcecaoBase {
    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
