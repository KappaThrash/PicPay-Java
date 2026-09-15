package bank.picpay.Dominio.Usuario.Dtos;

import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record UsuarioDto(
        @JsonProperty("usuario_id") UUID usuarioId,
        String nome,
        TipoUsuario tipo,
        String documento,
        String email
) {}