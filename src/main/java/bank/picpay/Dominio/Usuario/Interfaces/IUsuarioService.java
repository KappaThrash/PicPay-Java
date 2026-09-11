package bank.picpay.Dominio.Usuario.Interfaces;

import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {
    ResponseEntity<UsuarioEntity> validarUsuario(CPFDto dto);
    ResponseEntity<UsuarioEntity> validarLojista(CNPJDto dto);
}
