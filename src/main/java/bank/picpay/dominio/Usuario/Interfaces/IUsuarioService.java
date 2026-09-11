package bank.picpay.dominio.Usuario.Interfaces;

import bank.picpay.dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.dominio.Usuario.Entidades.cnpjDTO;
import bank.picpay.dominio.Usuario.Entidades.cpfDTO;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {
    ResponseEntity<UsuarioEntity> validarUsuario(cpfDTO dto);
    ResponseEntity<UsuarioEntity> validarLojista(cnpjDTO dto);
}
