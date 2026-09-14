package bank.picpay.Dominio.Usuario.Dtos;

import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPFDto {
    @NotBlank
    String nome;

    @NotNull
    TipoUsuario tipo;

    @CPF
    String cpf;

    @Email
    String email;

    @NotBlank
    String senha;
}
