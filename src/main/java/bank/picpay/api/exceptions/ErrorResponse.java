package bank.picpay.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}