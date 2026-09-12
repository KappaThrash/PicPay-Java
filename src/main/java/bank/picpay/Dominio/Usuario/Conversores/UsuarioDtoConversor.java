package bank.picpay.Dominio.Usuario.Conversores;

import bank.picpay.Dominio.Usuario.Dtos.UsuarioDto;
import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;

public class UsuarioDtoConversor {
    public static UsuarioDto Converter(UsuarioEntity entity){
        return new UsuarioDto(
                entity.getUsuario_id(), entity.getNome(),
                entity.getTipo(), entity.getDocumento(), entity.getEmail());
    }
}
