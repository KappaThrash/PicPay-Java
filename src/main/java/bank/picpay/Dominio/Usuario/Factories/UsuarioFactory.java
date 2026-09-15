package bank.picpay.Dominio.Usuario.Factories;

import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;

import java.util.UUID;

public class UsuarioFactory {

    public static UsuarioEntity usuarioTipoUSUARIO(){
        return UsuarioEntity.builder()
                .usuarioId(UUID.randomUUID())
                .nome("Daniel R K")
                .tipo(TipoUsuario.USUARIO)
                .documento("057.698.825-14")
                .email("danielkuhin@gmail.com")
                .senha("abc123").build();
    }

    public static UsuarioEntity usuarioTipoLOJISTA(){
        return UsuarioEntity.builder()
                .usuarioId(UUID.randomUUID())
                .nome("João Z P")
                .tipo(TipoUsuario.LOJISTA)
                .documento("63.876.897/0001-31")
                .email("joaozp@gmail.com")
                .senha("def").build();
    }
}
