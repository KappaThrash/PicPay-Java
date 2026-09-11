package bank.picpay.dominio.factories.factories;

import bank.picpay.dominio.models.usuario.TipoUsuario;
import bank.picpay.dominio.models.usuario.UsuarioEntity;

import java.util.UUID;

public class UsuarioFactory {

    public static UsuarioEntity usuarioTipoUSUARIO(){
        return UsuarioEntity.builder()
                .usuario_id(UUID.randomUUID())
                .nome("Daniel R K")
                .tipo(TipoUsuario.USUARIO)
                .documento("057.698.825-14")
                .email("danielkuhin@gmail.com")
                .senha("abc123").build();
    }

    public static UsuarioEntity usuarioTipoLOJISTA(){
        return UsuarioEntity.builder()
                .usuario_id(UUID.randomUUID())
                .nome("João Z P")
                .tipo(TipoUsuario.LOJISTA)
                .documento("63.876.897/0001-31")
                .email("joaozp@gmail.com")
                .senha("def").build();
    }

}
