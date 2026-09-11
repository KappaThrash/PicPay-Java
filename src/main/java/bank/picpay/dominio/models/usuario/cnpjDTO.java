package bank.picpay.dominio.models.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class cnpjDTO {
    @NotBlank
    String nome;

    @NotNull
    TipoUsuario tipo;

    @CNPJ
    String cnpj;

    @Email
    String email;

    @NotBlank
    String senha;
}
