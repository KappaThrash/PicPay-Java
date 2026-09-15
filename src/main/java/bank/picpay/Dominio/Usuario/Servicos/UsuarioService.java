package bank.picpay.Dominio.Usuario.Servicos;

import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Usuario.Dtos.UsuarioDto;
import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Conversores.UsuarioDtoConversor;
import bank.picpay.Dominio.Usuario.Interfaces.IUsuarioService;
import bank.picpay.Infra.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<UsuarioDto> validarUsuario(CPFDto dto){
        var entity = new UsuarioEntity();
        entity.mapCpfDtoToEntity(dto);

        var senha = passwordEncoder.encode(dto.getSenha());
        entity.setSenha(senha);

        repository.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDtoConversor.converter(entity));
    }

    public ResponseEntity<UsuarioDto> validarLojista(CNPJDto dto){
        var entity = new UsuarioEntity();
        entity.mapCnpjDtoToEntity(dto);

        var senha = passwordEncoder.encode(dto.getSenha());
        entity.setSenha(senha);

        repository.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDtoConversor.converter(entity));
    }
}
