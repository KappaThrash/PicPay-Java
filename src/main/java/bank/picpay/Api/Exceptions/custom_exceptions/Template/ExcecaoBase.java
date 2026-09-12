package bank.picpay.Api.Exceptions.custom_exceptions.Template;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExcecaoBase extends RuntimeException{

    protected final HttpStatus status;

    public ExcecaoBase(String message){
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public ExcecaoBase(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
