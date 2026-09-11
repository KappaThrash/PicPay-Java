package bank.picpay.dominio.models.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationResponseDTO {
    String status;
    DataResponseAuth data;
}
