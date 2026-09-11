package bank.picpay.dominio.models.carteira;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CarteiraDTO {
    @NotNull
    private UUID user_id;
}
