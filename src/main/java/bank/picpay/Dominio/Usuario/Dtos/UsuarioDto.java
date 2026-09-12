package bank.picpay.Dominio.Usuario.Dtos;

import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import java.util.UUID;

public record UsuarioDto(UUID usuario_id, String nome, TipoUsuario tipo, String documento, String email){}