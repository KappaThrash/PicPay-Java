package bank.picpay.dominio.service;

import bank.picpay.dominio.models.usuario.UsuarioEntity;
import bank.picpay.dominio.models.usuario.cnpjDTO;
import bank.picpay.dominio.models.usuario.cpfDTO;
import bank.picpay.infra.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<UsuarioEntity> validarUsuario(cpfDTO dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPFDTOoEntity(dto);

        repository.save(Entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(Entity);
    }

    public ResponseEntity<UsuarioEntity> validarLojista(cnpjDTO dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPNPJDTOoEntity(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }
}
